package myapplication.second.workinghourmanagement.dto.manageStaff

data class ResponseGetStaffInfo(
    val path: String,
    val method: String,
    val message: String,
    val data: StaffInfo,
    val statusCode: Int,
    val timestamp: String
)

data class StaffInfo(
    val employmentId: Int,
    val staffId: Int,
    val name: String,
    val phone: String,
    val profileImage: String?,
    val rank: String?,
    val hourlyWage: Int?,
    val readWorkTimesWithStaffList: List<WorkTime>
)

data class WorkTime(
    val workTimeId: Int,
    val dayOfWeek: String,
    val startTime: String,
    val endTime: String
)
