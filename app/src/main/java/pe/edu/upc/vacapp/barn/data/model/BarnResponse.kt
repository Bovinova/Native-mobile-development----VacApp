package pe.edu.upc.vacapp.barn.data.model

import pe.edu.upc.vacapp.barn.domain.model.Barn

data class BarnResponse(
    val id: Int,
    val name: String,
    val limit: Int
) {
    fun toBarn(): Barn {
        return Barn(
            name = name,
            limit = limit.toString()
        )
    }
}
