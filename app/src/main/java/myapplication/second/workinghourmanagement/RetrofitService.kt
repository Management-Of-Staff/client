package myapplication.second.workinghourmanagement

import myapplication.second.workinghourmanagement.dto.*

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

    @POST("auth/update-phone")
    fun updatePhone(
        @Header("Authorization") token: String,
        @Body params: HashMap<String, String>,
    ): Call<ResultResponse>

    @Multipart
    @POST("auth/update-profile")
    fun updateProfile(
        @Header("Authorization") token: String,
        @Part("profile") profile: HashMap<String, String>,
       // @Part imageFile: MultipartBody.Part
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
    ): Call<ResultBNumCheck>

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
    // 매장 리스트 불러오기 (가장 최신 매장이 위에 오도록 정렬해야 함)
    @GET("stores/")
    fun getStoreList(
        @Header("Authorization") token: String,
//        @Query("timestamp") timestamp: List<Int>
    ): Call<ResultGetStoreList>

    // 매장 등록
    @POST("stores/")
    fun postStore(
        @Header("Authorization") token: String,
        @Body params: HashMap<String, String>
    ): Call<ResultResponse>

    // 매장 수정
    @POST("stores/{storeId}")
    fun modifyStore(
        @Header("Authorization") token: String,
        @Body params: HashMap<String, String>,
        @Path("storeId") storeId: String
    ): Call<ResultResponse>

    // 매장 삭제
    @DELETE("stores/{storeId}")
    fun deleteStore(
        @Path("storeId") storeId: String
    ): Call<Unit>


/***    매장 일정    ***/
/***    Owner    ***/
}