package myapplication.second.workinghourmanagement.dto.todo_list

data class ResponseGetStaffTodo(
    val path: String,
    val method: String,
    val message: String,
    val data: String,
    val statusCode: Int,
    val timestamp: String
)

data class StaffTodoDto(
    val todoListId: Int,
    val storeId: Int,
    val todoListTitle: String,
    val managerResponseDtoList: ManagerResponseDtoList,
    val taskStartTime: String,
    val todoListDetailResponseDtoList: TodoListDetailResponseDtoList
)

data class ManagerResponseDtoList(
    val staffId: Int,
    val mangerName: String
)

data class TodoListDetailResponseDtoList(
    val todoListDetailId: Int,
    val todoListDetail: String
)