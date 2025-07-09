package pe.edu.upc.vacapp.barn.data.model

data class BarnResponse(
    val id: Int,
    val name: String,
    val limit: Int
) {
    fun toBarnEntity(userId: Int): BarnEntity {
        return BarnEntity(
            id = this.id,
            name = this.name,
            limit = this.limit.toString(),
            userId = userId,
            synced = true
        )
    }
}
