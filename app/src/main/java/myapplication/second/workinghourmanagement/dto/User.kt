package myapplication.second.workinghourmanagement.dto

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("uuid") val uuid: String,
    @SerializedName("name") val name: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("password") val password: String,
    @SerializedName("role") val role: String,
)
