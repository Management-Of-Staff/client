package myapplication.second.workinghourmanagement.dto

data class ResultGetOwnerStaffTodo(
    val todoId: Int,
    val startTime: List<Int>,
    val managerName: String
)
