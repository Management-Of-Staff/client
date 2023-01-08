package myapplication.second.workinghourmanagement

import myapplication.second.workinghourmanagement.dto.ResultLogin
import myapplication.second.workinghourmanagement.dto.ResultResponse
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {
    // Owner
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
    fun loginOwner(
        @Body params: HashMap<String, String>,
    ): Call<ResultLogin>

    @GET("owners/{token}")
    fun getOwner(
        @Path("token") token: String?
    ): Call<PostResult>

    // Staff

    @GET("posts/{post}")
    fun getPosts(
        @Path("post") post: String?
    ): Call<PostResult>
}