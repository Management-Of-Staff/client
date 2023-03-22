package myapplication.second.workinghourmanagement.retrofit

import myapplication.second.workinghourmanagement.dto.ResultResponse
import myapplication.second.workinghourmanagement.dto.ResultToken
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginService {
    @GET("${path}/log-out")
    fun logOut():Call<ResultResponse>

    @POST("${path}/reissue")
    fun reissue(): Call<ResultToken>

    @POST("${path}/sign-in/owner")
    fun signInOwner(
        @Body params: HashMap<String, String>
    ): Call<ResultToken>

    @POST("${path}/sign-in/staff")
    fun signInStaff(
        @Body params: HashMap<String, String>
    ): Call<ResultToken>

    companion object {
        const val path = "member-account"
    }
}