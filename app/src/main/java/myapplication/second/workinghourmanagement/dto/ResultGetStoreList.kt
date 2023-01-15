package myapplication.second.workinghourmanagement.dto

data class ResultGetStoreList(
    val path: String,
    val method: String,
    val message: String,
    val data: List<ResultGetStore>,
    val statusCode: Int,
    val timestamp: List<Int>
)
