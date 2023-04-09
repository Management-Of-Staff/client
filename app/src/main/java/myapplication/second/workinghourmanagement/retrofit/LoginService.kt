package myapplication.second.workinghourmanagement.retrofit

import myapplication.second.workinghourmanagement.dto.ResultResponse
import myapplication.second.workinghourmanagement.dto.ResultToken
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginService {
    @GET("${memberPath}/log-out")
    fun logOut(): Call<ResultResponse>

    @POST("${memberPath}/reissue")
    fun reissue(): Call<ResultToken>

    @POST("${signInPath}/owner")
    fun signInOwner(
        @Body params: HashMap<String, String>
    ): Call<ResultToken>

    @POST("${signInPath}/staff")
    fun signInStaff(
        @Body params: HashMap<String, String>
    ): Call<ResultToken>

    companion object {
        const val memberPath = "member-account"
        const val signInPath = "member-account/sign-in"
    }
}