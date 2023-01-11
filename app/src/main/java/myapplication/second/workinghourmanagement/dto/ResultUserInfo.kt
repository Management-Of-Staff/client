package myapplication.second.workinghourmanagement.dto

data class ResultUserInfo(
    val path: String,
    val method: String,
    val message: String,
    val data: User,
    val statusCode: Int,
    val timestamp: List<Int>,
)
data class User(
    val name: String,
    val phone: String,
    val email: String?,
    val birthDate: String?,
    val role: String,
    val uuid: String
)
