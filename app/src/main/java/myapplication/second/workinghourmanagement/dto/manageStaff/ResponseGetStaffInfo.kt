package myapplication.second.workinghourmanagement.dto.manageStaff

data class ResponseGetStaffInfo(
    val path: String,
    val method: String,
    val message: String,
    val data: StaffInfo,
    val statusCode: Int,
    val timestamp: String
)
