package myapplication.second.workinghourmanagement.dto

data class ResultUserInfo(
    val path: String,
    val method: String,
    val message: String,
    val data: UserParcelable,
    val statusCode: Int,
    val timestamp: List<Int>,
)
