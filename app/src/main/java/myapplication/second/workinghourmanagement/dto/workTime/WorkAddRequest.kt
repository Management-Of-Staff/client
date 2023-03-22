package myapplication.second.workinghourmanagement.dto.workTime

data class WorkAddRequest(
    val dayOfWeekList: List<String>,
    val endTime: String,
    val staffId: Int,
    val startTime: String
)