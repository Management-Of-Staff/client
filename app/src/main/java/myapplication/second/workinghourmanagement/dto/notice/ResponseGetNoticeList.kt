package myapplication.second.workinghourmanagement.dto.notice

data class ResponseGetNoticeList(
    val path: String,
    val method: String,
    val message: String,
    val data: List<ResponseGetNotice>,
    val statusCode: Int,
    val timestamp: List<Int>
)
