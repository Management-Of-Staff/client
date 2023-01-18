package myapplication.second.workinghourmanagement.dto

data class ResultGetNotice(
    val noticeId: Int,
    val noticeTitle: String,
    val noticeDesc: String,
    val noticeUsers: List<String>,
    val noticeImageList: List<String>
)
