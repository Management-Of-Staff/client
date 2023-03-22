package myapplication.second.workinghourmanagement.dto.workTime

data class WorkDeleteResponse(
    val path: String,
    val method: String,
    val message: String,
    val data: String,
    val statusCode: Int,
    val timestamp: String,
)