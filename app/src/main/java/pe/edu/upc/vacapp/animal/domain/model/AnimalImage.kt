package pe.edu.upc.vacapp.animal.domain.model

import java.io.File

sealed class AnimalImage {
    data class FromFile(val file: File) : AnimalImage()
    data class FromUrl(val url: String) : AnimalImage()
}
