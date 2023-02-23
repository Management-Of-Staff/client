package myapplication.second.workinghourmanagement.dto.store

data class ResponseGetStore(
    val storeId: Int,
    val storeName: String,
    val branchName: String
)