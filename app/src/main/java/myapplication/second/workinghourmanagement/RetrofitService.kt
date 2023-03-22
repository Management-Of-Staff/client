package myapplication.second.workinghourmanagement

import myapplication.second.workinghourmanagement.dto.ResultBNumCheck
import myapplication.second.workinghourmanagement.dto.ResultResponse
import myapplication.second.workinghourmanagement.dto.ResultToken
import myapplication.second.workinghourmanagement.dto.ResultUserInfo
import myapplication.second.workinghourmanagement.dto.manageStaff.ResponseFindStaff
import myapplication.second.workinghourmanagement.dto.manageStaff.ResponseGetStaffInfo
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
import retrofit2.Response
import retrofit2.http.*

interface RetrofitService {
    @POST("member-account/reissue")
    fun reissue(): Call<ResultToken>

    @GET("member-account/log-out")
    fun logOut():Call<ResultResponse>

    @POST("member-account/sign-in/owner")
    fun signInOwner(
        @Body params: HashMap<String, String>
    ): Call<ResultToken>

    @POST("auth/login")
    fun login(
        @Body params: HashMap<String, String>
    ): Call<ResultToken>

    @POST("member-account/owners/password-check")
    fun passwordCheckOwner(
        @Body params: HashMap<String, String>,
    ): Call<ResultResponse>

    @POST("member-account/owners/password")
    fun updatePasswordOwner(
        @Body params: HashMap<String, String>,
    ): Call<ResultResponse>

    @POST("member-account/owners/phone")
    fun updatePhoneOwner(
        @Body params: HashMap<String, String>,
    ): Call<ResultResponse>

    @Multipart
    @POST("member-account/owners/profile")
    fun updateProfileOwner(
        @Part imageFile: MultipartBody.Part?,
        @Part("profile") profile: HashMap<String, String>
    ): Call<ResultResponse>

    @PUT("member-account/owners")
    fun withDraw(
    ): Call<ResultResponse>

    @GET("member-account/owners")
    fun selectOwnerInfo(
    ): Call<ResultUserInfo>

    // 사업자 등록번호 조회
    @Headers("Content-Type: application/json")
    @POST("status")
    fun checkBNum(
        @Query("serviceKey") serviceKey: String,
        @Body params: HashMap<String, List<String>>
    ): Call<ResultBNumCheck>

    @Headers("Content-Type: application/json")
    @POST("member-account/owners/sign-up")
    fun signUpOwner(
        @Body params: HashMap<String, String>
    ): Call<ResultResponse>

    // Staff
    @GET("staffs/")
    fun selectStaffInfo(
        @Header("Authorization") token: String,
    ): Call<ResultUserInfo>

    // 직원 검색
    @POST("employment/find-invitee")
    fun findStaff(
        @Body params: HashMap<String, String>
    ): Call<ResponseFindStaff>

    // 매장에서 근무하는 직원 목록
    @GET("employment/read-all")
    fun getStaffList(
        @Query("storeId") storeId: Int,
    ): Call<StaffList>

    @GET("employment/read")
    fun getStaffInfo(
        @Query("staffId") staffId: Int,
        @Query("storeId") storeId: Int
    ): Call<ResponseGetStaffInfo>

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