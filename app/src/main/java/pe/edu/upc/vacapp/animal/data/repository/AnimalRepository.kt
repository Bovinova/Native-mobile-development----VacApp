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
import pe.edu.upc.vacapp.shared.data.local.JwtStorage
import pe.edu.upc.vacapp.shared.isOnline
import java.io.File

class AnimalRepository(
    private val animalService: AnimalService,
    private val animalDao: AnimalDao,
    private val barnDao: BarnDao
) {
    suspend fun addAnimal(animal: Animal) = withContext(Dispatchers.IO) {
        val animalEntity = AnimalEntity.fromAnimal(animal)
        animalDao.insertAnimal(animalEntity)

        if (isOnline()) {
            try {
                val req = AddAnimalRequest.fromAnimal(animal)
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
                    animalDao.updateSyncedStatus(animal.id, true)
                } else {
                    throw Exception("Error adding animal: ${res.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                throw Exception("Error de red: ${e.message}")
            }
        }
    }

    suspend fun getAllAnimals(): List<Animal> = withContext(Dispatchers.IO) {
        val localAnimals = animalDao.getAllAnimals().map { it.toAnimal() }

        if (isOnline()) {
            val response = animalService.getAllAnimals()

            if (response.isSuccessful) {
                val animalsFromApi = response.body()?.map { it.toAnimalEntity() } ?: emptyList()

                animalDao.clearAnimals()
                animalsFromApi.forEach { animalDao.insertAnimal(it) }

                return@withContext animalsFromApi.map { it.toAnimal() }
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

    suspend fun copyFileToInternalStorage(sourceFile: File): File? = withContext(Dispatchers.IO) {
        val context = Vacapp.instance.applicationContext

        return@withContext try {
            val destFile = File(context.filesDir, "animal_${System.currentTimeMillis()}.jpg")
            sourceFile.inputStream().use { input ->
                destFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            destFile
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
