package myapplication.second.workinghourmanagement.dto.notice

data class ResponseGetNotice(
    val noticeId: Int,
    val noticeTitle: String,
    val noticeDesc: String,
    val noticeUsers: List<String>,
    val noticeImageList: List<String>
)
