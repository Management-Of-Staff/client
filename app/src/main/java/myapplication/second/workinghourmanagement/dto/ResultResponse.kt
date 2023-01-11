package myapplication.second.workinghourmanagement.dto

data class ResultResponse(
    val path: String,
    val method: String,
    val message: String,
    val data: String,
    val statusCode: Int,
    val timestamp: List<Int>,
)
