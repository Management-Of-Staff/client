package myapplication.second.workinghourmanagement.dto.todo_list

data class TodoListCreateDto(
    val managerCreateDtoList: ManagerCreateDtoList,
    val taskStartTime: String,
    val todoListDetailCreateDtoList: TodoListDetailCreateDtoList,
    val todoListTitle: String
)

data class ManagerCreateDtoList(
    val managerId: Int,
    val mangerName: String,
    val staffId: Int
)

data class TodoListDetailCreateDtoList(
    val completeCheck: String,
    val completeTime: String,
    val todoListDetail: String,
    val todoListDetailId: Int
)