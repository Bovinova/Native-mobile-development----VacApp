package pe.edu.upc.vacapp.barn.data.model

import pe.edu.upc.vacapp.barn.domain.model.Barn

data class CreateBarnRequest(
    val name: String,
    val limit: String,
    val id: Int
) {
    companion object {
        fun fromBarn(b: Barn): CreateBarnRequest {
            return CreateBarnRequest(
                name = b.name,
                limit = b.limit,
                id = 1
            )
        }
    }
}
