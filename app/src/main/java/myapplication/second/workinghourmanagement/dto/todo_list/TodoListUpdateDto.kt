package myapplication.second.workinghourmanagement.dto.todo_list

data class TodoListUpdateDto(
    val managerCreateDtoList: ManagerCreateDtoList,
    val taskStartTime: String,
    val todoListDetailCreateDtoList: TodoListDetailCreateDtoList,
    val todoListTitle: String
)