package myapplication.second.workinghourmanagement.dto.manageStaff

import com.google.gson.annotations.SerializedName

data class UpdateRankAndWageRequest(
    @SerializedName("hourlyWage")
    val hourlyWage: Int,
    @SerializedName("rank")
    val rank: String
)
