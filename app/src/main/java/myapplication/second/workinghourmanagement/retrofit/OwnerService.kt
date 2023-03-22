package myapplication.second.workinghourmanagement.retrofit

import myapplication.second.workinghourmanagement.dto.ResultBNumCheck
import myapplication.second.workinghourmanagement.dto.ResultResponse
import myapplication.second.workinghourmanagement.dto.ResultUserInfo
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface OwnerService {
    // [사장] 정보 조회
    @GET(path)
    fun selectOwnerInfo(
    ): Call<ResultUserInfo>

    // [사장] 회원 탈퇴
    @PUT(path)
    fun withDraw(
    ): Call<ResultResponse>

    // [사장] 비밀번호 변경
    @POST("${path}/password")
    fun updatePasswordOwner(
        @Body params: HashMap<String, String>,
    ): Call<ResultResponse>

    //[사장] 비밀번호 확인
    @POST("${path}s/password-check")
    fun passwordCheckOwner(
        @Body params: HashMap<String, String>,
    ): Call<ResultResponse>

    // [사장] 핸드폰 번호 수정
    @POST("${path}/phone")
    fun updatePhoneOwner(
        @Body params: HashMap<String, String>,
    ): Call<ResultResponse>

    //[사장] 프로필 정보 변경
    @Multipart
    @POST("${path}/profile")
    fun updateProfileOwner(
        @Part imageFile: MultipartBody.Part?,
        @Part("profile") profile: HashMap<String, String>
    ): Call<ResultResponse>

    // [사장] 회원 가입
    @POST("${path}/sign-up")
    fun signUpOwner(
        @Body params: HashMap<String, String>
    ): Call<ResultResponse>

    // 사업자 등록번호 조회
    @POST("status")
    fun checkBNum(
        @Query("serviceKey") serviceKey: String,
        @Body params: HashMap<String, List<String>>
    ): Call<ResultBNumCheck>

    companion object {
        const val path = "member-account/owners"
    }
}