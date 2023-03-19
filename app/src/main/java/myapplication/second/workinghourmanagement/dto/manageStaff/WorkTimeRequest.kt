package myapplication.second.workinghourmanagement.dto.manageStaff

data class WorkTimeRequest(
    val dayOfWeekList: List<String>,
    val startDate: String,
    val endDate: String,
    val startTime: String,
    val endTime: String
)