package myapplication.second.workinghourmanagement.dto.store

data class ResponseRegisterStore(
    val path: String,
    val method: String,
    val message: String,
    val data: String,
    val statusCode: Int,
    val timestamp: String
)

