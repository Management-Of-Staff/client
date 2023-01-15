package myapplication.second.workinghourmanagement.dto

data class ResultGetStaffList(
    val path: String,
    val method: String,
    val message: String,
    val data: List<ResultGetStaff>,
    val statusCode: Int,
    val timestamp: List<Int>
)
