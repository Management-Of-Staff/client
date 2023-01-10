package myapplication.second.workinghourmanagement

import myapplication.second.workinghourmanagement.dto.ResultGetStore
import myapplication.second.workinghourmanagement.dto.ResultLogin
import myapplication.second.workinghourmanagement.dto.ResultResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

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


/***    매장 관리    ***/
/***    Owner    ***/
    // 매장 리스트 불러오기
    @GET("stores")
    fun getStoreList(
        @Path("ownerId") ownerId: String
    ): Call<List<ResultGetStore>>

    // 매장 등록
    @POST("stores")
    fun postStore(
        @Body params: HashMap<String, String>
    ): Call<ResultResponse>

    // 매장 수정
    @POST("stores")
    fun modifyStore(
        @Path("post") post: String
    ): Call<PostResult>

    // 매장 삭제
    @DELETE("stores/{storeId}")
    fun deleteStore(
        @Path("storeId") storeId: String
    ): Call<PostResult>


/***    매장 일정    ***/
/***    Owner    ***/
}