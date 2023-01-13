package myapplication.second.workinghourmanagement

import myapplication.second.workinghourmanagement.dto.ResultGetStore
import myapplication.second.workinghourmanagement.dto.ResultBnumCheck
import myapplication.second.workinghourmanagement.dto.ResultLogin
import myapplication.second.workinghourmanagement.dto.ResultResponse
import myapplication.second.workinghourmanagement.dto.ResultUserInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

import retrofit2.http.*

interface RetrofitService {
    @POST("auth/login")
    fun login(
        @Body params: HashMap<String, String>,
    ): Call<ResultLogin>

    @POST("auth/check-password")
    fun checkPassword(
        @Header("Authorization") token: String,
        @Body params: HashMap<String, String>,
    ): Call<ResultResponse>

    @POST("auth/update-password")
    fun updatePassword(
        @Header("Authorization") token: String,
        @Body params: HashMap<String, String>,
    ): Call<ResultResponse>

    // Owner
    @GET("owners/")
    fun selectOwnerInfo(
        @Header("Authorization") token: String,
    ): Call<ResultUserInfo>

    // 사업자 등록번호 조회
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

    // Staff
    @GET("staffs/")
    fun selectStaffInfo(
        @Header("Authorization") token: String,
    ): Call<ResultUserInfo>

    @GET("posts/{post}")
    fun getPosts(
        @Path("post") post: String?
    ): Call<PostResult>


/***    매장 관리    ***/
/***    Owner    ***/
    // 매장 리스트 불러오기
    @GET("stores")
    fun getStoreList(
        @Header("token") token: String
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