package myapplication.second.workinghourmanagement.dto.workTime

data class WorkUpdateRequest(
    val workAddRequest: WorkAddRequest,
    val workTimeIds: List<Int>
)