package pe.edu.upc.vacapp.animal.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.vacapp.Vacapp
import pe.edu.upc.vacapp.animal.data.local.AnimalDao
import pe.edu.upc.vacapp.animal.data.model.AddAnimalRequest
import pe.edu.upc.vacapp.animal.data.model.AnimalEntity
import pe.edu.upc.vacapp.animal.data.model.toMultipartPart
import pe.edu.upc.vacapp.animal.data.model.toRequestBody
import pe.edu.upc.vacapp.animal.data.remote.AnimalService
import pe.edu.upc.vacapp.animal.domain.model.Animal
import pe.edu.upc.vacapp.barn.data.local.BarnDao
import pe.edu.upc.vacapp.barn.domain.model.Barn
import pe.edu.upc.vacapp.home.data.local.UserInfoDao
import pe.edu.upc.vacapp.shared.createPendingOperation
import pe.edu.upc.vacapp.shared.data.local.JwtStorage
import pe.edu.upc.vacapp.shared.data.local.PendingOperationDao
import pe.edu.upc.vacapp.shared.isOnline
import java.io.File
import java.net.URL

class AnimalRepository(
    private val animalService: AnimalService,
    private val animalDao: AnimalDao,
    private val barnDao: BarnDao,
    private val pendingOperationDao: PendingOperationDao,
    private val userInfoDao: UserInfoDao
) {
    suspend fun addAnimal(animal: Animal) = withContext(Dispatchers.IO) {
        val userId = JwtStorage.getUserId() ?: throw Exception("User not authenticated")

        val lastId = animalDao.getLastId() ?: 0
        val generatedId = lastId + 1

        val finalImagePath = copyImageToInternalStorageIfNeeded(animal.image)
        val updatedAnimal = animal.copy(image = finalImagePath)
        val animalEntity = AnimalEntity.fromAnimal(updatedAnimal, userId, generatedId)

        animalDao.insert(animalEntity)
        userInfoDao.increaseTotalAnimals(userId)

        if (isOnline()) {
            try {
                val req = AddAnimalRequest.fromAnimal(updatedAnimal)
                val res = animalService.addAnimal(
                    req.name.toRequestBody(),
                    req.gender.toRequestBody(),
                    req.birthDate.toRequestBody(),
                    req.breed.toRequestBody(),
                    req.location.toRequestBody(),
                    req.stableId.toRequestBody(),
                    req.image.toMultipartPart("FileData")
                )

                if (res.isSuccessful) {
                    val animalFromApi = res.body()

                    if (animalFromApi != null) {
                        animalDao.deleteById(generatedId)

                        val newAnimalEntity = animalFromApi.toAnimalEntity(userId)
                        animalDao.insert(newAnimalEntity)
                    }

                    animalDao.updateSyncedStatus(animal.id, true)
                    animalDao.deleteById(generatedId)
                } else {
                    throw Exception("Error adding animal: ${res.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                throw Exception("Error de red: ${e.message}")
            }
        } else {
            val barn = barnDao.getBarnById(animalEntity.stableId)
                ?: throw Exception("Barn not found for campaign")

            val localId = if (!barn.synced) barn.id else 0

            val pendingOperation = createPendingOperation(
                entity = "animal",
                data = animalEntity,
                localId = localId
            )

            pendingOperationDao.insert(pendingOperation)
        }
    }

    suspend fun getAllAnimals(): List<Animal> = withContext(Dispatchers.IO) {
        val userId = JwtStorage.getUserId() ?: throw Exception("User not authenticated")

        val localAnimals = animalDao.getAnimalsByUserId(userId).map { it.toAnimal() }

        if (isOnline()) {
            val response = animalService.getAllAnimals()

            if (response.isSuccessful) {
                val animalsFromApi =
                    response.body()?.map { it.toAnimalEntity(userId) } ?: emptyList()

                animalsFromApi.forEach { animalEntity ->
                    val localPath =
                        downloadImageToInternalStorage(animalEntity.imagePath, animalEntity.id)

                    val existingAnimal = animalDao.getAnimalById(animalEntity.id)
                    if (existingAnimal == null) {
                        val animalWithUpdatedPath =
                            animalEntity.copy(imagePath = localPath ?: animalEntity.imagePath)
                        animalDao.insert(animalWithUpdatedPath)
                    } else {
                        if (localPath != null) {
                            animalDao.updateSyncedStatus(animalEntity.id, true)
                        }
                    }
                }

                return@withContext animalDao.getAnimalsByUserId(userId).map { it.toAnimal() }
            }
        }

        return@withContext localAnimals
    }

    suspend fun getBarns(): List<Barn> = withContext(Dispatchers.IO) {
        val userId = JwtStorage.getUserId() ?: throw Exception("User not authenticated")

        val localBarns = barnDao.getBarnsByUserId(userId).map { it.toBarn() }

        if (isOnline()) {
            val response = animalService.getBarns()

            if (response.isSuccessful) {
                val barnsFromApi = response.body()?.map { it.toBarnEntity(userId) } ?: emptyList()

                barnsFromApi.forEach { barnDao.insert(it) }

                return@withContext barnsFromApi.map { it.toBarn() }
            }
        }

        return@withContext localBarns
    }

    suspend fun downloadImageToInternalStorage(url: String, animalId: Int): String? =
        withContext(Dispatchers.IO) {
            val context = Vacapp.instance.applicationContext
            val destFile = File(context.filesDir, "animal_$animalId.jpg")

            return@withContext try {
                val input = URL(url).openStream()
                input.use { inputStream ->
                    destFile.outputStream().use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
                destFile.absolutePath
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

    private fun copyImageToInternalStorageIfNeeded(imagePath: String): String {
        val context = Vacapp.instance.applicationContext
        val sourceFile = File(imagePath)

        if (sourceFile.absolutePath.startsWith(context.filesDir.absolutePath)) {
            return imagePath
        }

        val destDir = File(context.filesDir, "animals")
        if (!destDir.exists()) destDir.mkdirs()

        val destFile = File(destDir, "animal_${System.currentTimeMillis()}.jpg")
        sourceFile.copyTo(destFile, overwrite = true)

        return destFile.absolutePath
    }
}
