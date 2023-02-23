package myapplication.second.workinghourmanagement.dto.todo_list

data class ResponseModifyStaffTodo(
    val path: String,
    val method: String,
    val message: String,
    val data: String,
    val statusCode: Int,
    val timestamp: String
)