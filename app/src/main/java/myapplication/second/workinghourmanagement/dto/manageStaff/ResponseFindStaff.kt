package myapplication.second.workinghourmanagement.dto.manageStaff

data class ResponseFindStaff(
    val path: String,
    val method: String,
    val message: String,
    val data: FindStaff,
    val statusCode: Int,
    val timestamp: String
)