package myapplication.second.workinghourmanagement.dto

import org.json.JSONObject

data class ResultResponse(
    val path: String,
    val method: String,
    val message: String,
    val data: JSONObject,
    val statusCode: Int,
    val timestamp: List<Int>,
)
