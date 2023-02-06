package myapplication.second.workinghourmanagement.dto

data class ResultToken(
    val accessToken: String?,
    val refreshToken: String?,

    val path: String?,
    val method: String?,
    val message: String?,
    val data: String?,
    val statusCode: Int?,
    val timestamp: List<Int>?,
)