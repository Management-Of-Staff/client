package myapplication.second.workinghourmanagement

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface RetrofitService {

    //    @FormUrlEncoded
//    @POST("/owners/create")
//    fun joinOwner(
//        @Field("userInfo") userInfo: Join,
//        @Field("UUID") source: String,
//        @Field("name") target: String,
//        @Field("phone") phone: String,
//        @Field("password") password: String,
//        @Field("role") role: String,
//        ): Call<String>
    @GET("/posts/{post}")
    fun getPosts(
        @Path("post") post: String?
    ): Call<PostResult>
}