package myapplication.second.workinghourmanagement

import myapplication.second.workinghourmanagement.dto.ResultBnumCheck
import myapplication.second.workinghourmanagement.dto.ResultLogin
import myapplication.second.workinghourmanagement.dto.ResultResponse
import myapplication.second.workinghourmanagement.dto.ResultUserInfo
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {
    @POST("auth/check-password")
    fun checkPassword(
        @Header("Authorization") token: String,
        @Body params: HashMap<String, String>,
    ): Call<ResultResponse>

    // Owner
    @GET("owners/")
    fun selectOwnerInfo(
        @Header("Authorization") token: String,
    ): Call<ResultUserInfo>

    @Headers("Content-Type: application/json")
    @POST("status")
    fun checkBNum(
        @Query("serviceKey") serviceKey: String,
        @Body params: HashMap<String, List<String>>
    ): Call<ResultBnumCheck>

    //    @FormUrlEncoded
    @Headers("Content-Type: application/json")
    @POST("owners/register")
    fun registerOwner(
//        @Field("uuid") uuid: String,
//        @Field("name") name: String,
//        @Field("phone") phone: String,
//        @Field("password") password: String,
//        @Field("role") role: String,
        @Body params: HashMap<String, String>
    ): Call<ResultResponse>

    @POST("auth/login")
    fun login(
        @Body params: HashMap<String, String>,
    ): Call<ResultLogin>

    // Staff
    @GET("staffs/")
    fun selectStaffInfo(
        @Header("Authorization") token: String,
    ): Call<ResultUserInfo>

    @GET("posts/{post}")
    fun getPosts(
        @Path("post") post: String?
    ): Call<PostResult>
}