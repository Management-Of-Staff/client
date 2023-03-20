package myapplication.second.workinghourmanagement.retrofit

import myapplication.second.workinghourmanagement.dto.ResultResponse
import myapplication.second.workinghourmanagement.dto.ResultToken
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginService {
    @GET("member-account/log-out")
    fun logOut():Call<ResultResponse>

    @POST("member-account/reissue")
    fun reissue(): Call<ResultToken>

    @POST("member-account/sign-in/owner")
    fun signInOwner(
        @Body params: HashMap<String, String>
    ): Call<ResultToken>

    @POST("member-account/sign-in/staff")
    fun signInStaff(
        @Body params: HashMap<String, String>
    ): Call<ResultToken>
}