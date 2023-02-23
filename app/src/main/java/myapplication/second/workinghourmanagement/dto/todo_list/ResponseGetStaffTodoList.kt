package myapplication.second.workinghourmanagement.dto.todo_list

data class ResponseGetStaffTodoList(
    val path: String,
    val method: String,
    val message: String,
    val data: List<ResponseGetStaffTodo>,
    val statusCode: Int,
    val timestamp: String
)