package myapplication.second.workinghourmanagement.dto.store

data class ResponseGetStoreList(
    val path: String,
    val method: String,
    val message: String,
    val data: List<ResponseGetStore>,
    val statusCode: Int,
    val timestamp: String
)