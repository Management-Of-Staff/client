package myapplication.second.workinghourmanagement

import myapplication.second.workinghourmanagement.dto.*
import myapplication.second.workinghourmanagement.dto.manageStaff.StaffList
import myapplication.second.workinghourmanagement.dto.store.ResponseGetStoreList
import myapplication.second.workinghourmanagement.dto.store.ResponseModifyStore
import myapplication.second.workinghourmanagement.dto.store.ResponseRegisterStore
import myapplication.second.workinghourmanagement.dto.todo_list.ResponseGetStaffTodo
import myapplication.second.workinghourmanagement.dto.todo_list.ResponseGetStaffTodoList
import myapplication.second.workinghourmanagement.dto.todo_list.ResponseModifyStaffTodo
import myapplication.second.workinghourmanagement.dto.todo_list.ResponsePostStaffTodo
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {
    @POST("auth/reissue")
    fun reissue(): Call<ResultToken>

    @POST("auth/login")
    fun login(
        @Body params: HashMap<String, String>
    ): Call<ResultToken>

    @POST("auth/check-password")
    fun checkPassword(
        @Body params: HashMap<String, String>,
    ): Call<ResultResponse>

    @POST("auth/update-password")
    fun updatePassword(
        @Body params: HashMap<String, String>,
    ): Call<ResultResponse>

    @POST("auth/update-phone")
    fun updatePhone(
        @Body params: HashMap<String, String>,
    ): Call<ResultResponse>

    @Multipart
    @POST("auth/update-profile")
    fun updateProfile(
        @Part imageFile: MultipartBody.Part?,
        @Part("profile") profile: HashMap<String, String>
    ): Call<ResultResponse>

    @POST("auth/withdrawal-member")
    fun withDraw(
    ): Call<ResultResponse>

    // Owner
    @GET("owners/")
    fun selectOwnerInfo(
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

    // 매장에서 근무하는 직원 목록
    @GET("employment/read/")
    fun getStaffList(
        @Query("storeId") storeId: Int,
    ): Call<StaffList>


    /***    매장 관리    ***/
    /***    Owner    ***/
    // 매장 리스트 불러오기 (가장 최신 매장이 위에 오도록 정렬해야 함)
    @GET("stores/")
    fun getStoreList(
    ): Call<ResponseGetStoreList>

    // 매장 생성
    @POST("stores/")
    fun postStore(
        @Body params: HashMap<String, String>
    ): Call<ResponseRegisterStore>

    // 매장 수정
    @POST("stores/{storeId}")
    fun modifyStore(
        @Path("storeId") storeId: Int,
        @Body params: HashMap<String, String>
    ): Call<ResponseModifyStore>

    // 매장 삭제
    @DELETE("stores/{storeId}")
    fun deleteStore(
        @Path("storeId") storeId: Int
    ): Call<Unit>


    /***    매장 일정    ***/
    /***    Owner    ***/

    /***    해야할 일    ***/
    // 직원 해야할 일 목록 불러오기
    @GET("todoList/")
    fun getStaffTodoList(
        @Query("storeId") storeId: Int
    ): Call<ResponseGetStaffTodoList>

    // 직원 해야할 일 등록
    @POST("todoList/")
    fun postStaffTodo(
        @Query("storeId") storeId: Int,
        @Body params: HashMap<String, String>
    ): Call<ResponsePostStaffTodo>

    // 직원 해야할 일 상세 정보 불러오기
    @GET("todoList/{todoListId}")
    fun getStaffTodo(
        @Path("todoListId") todoListId: Int
    ): Call<ResponseGetStaffTodo>

    // 직원 해야할 일 수정
    @POST("todoList/{todoListId}")
    fun modifyStaffTodo(
        @Query("storeId") storeId: Int,
        @Path("todoListId") todoListId: Int,
        @Body params: HashMap<String, String>
    ): Call<ResponseModifyStaffTodo>

    // 매장 삭제
    @DELETE("todoList/{todoListId}")
    fun deleteStaffTodo(
        @Path("todoListId") todoListId: Int
    ): Call<Unit>
}