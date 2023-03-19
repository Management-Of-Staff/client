package myapplication.second.workinghourmanagement.dto.manageStaff

data class StaffInfo(
    val employmentId: Long,
    val staffId: Int,
    val name: String,
    val phone: String,
    val profilePath: String?,
    val rank: String,
    val hourlyWage: Int,
    val readWorkTimesWithStaffList: List<WorkTimeRequest>
)
