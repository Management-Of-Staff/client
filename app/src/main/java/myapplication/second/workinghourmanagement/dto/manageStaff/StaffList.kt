package myapplication.second.workinghourmanagement.dto.manageStaff

data class StaffList(
    val path: String,
    val method: String,
    val message: String,
    val data: List<Staff>,
    val statusCode: Int,
    val timestamp: String
)
