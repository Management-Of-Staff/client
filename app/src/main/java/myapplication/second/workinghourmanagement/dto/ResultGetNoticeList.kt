package myapplication.second.workinghourmanagement.dto

data class ResultGetNoticeList(
    val path: String,
    val method: String,
    val message: String,
    val data: List<ResultGetNotice>,
    val statusCode: Int,
    val timestamp: List<Int>
)
