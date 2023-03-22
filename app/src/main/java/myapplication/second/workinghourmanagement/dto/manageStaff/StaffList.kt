package myapplication.second.workinghourmanagement.dto.manageStaff

data class StaffList(
    val path: String,
    val method: String,
    val message: String,
    val data: Map<String, List<Staff>>,
    val statusCode: Int,
    val timestamp: String
)

data class Staff(
    val employmentId: Int,
    val staffName: String,
    val profileImage: String?,
    val day: String?,
    val startTime: String?,
    val endTime: String?,
    val attendanceStatus: String
)
