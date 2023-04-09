package myapplication.second.workinghourmanagement.retrofit

import myapplication.second.workinghourmanagement.dto.ResultResponse
import myapplication.second.workinghourmanagement.dto.manageStaff.ResponseFindStaff
import myapplication.second.workinghourmanagement.dto.manageStaff.ResponseGetStaffInfo
import myapplication.second.workinghourmanagement.dto.manageStaff.StaffList
import myapplication.second.workinghourmanagement.dto.manageStaff.UpdateRankAndWageRequest
import retrofit2.Call
import retrofit2.http.*

interface ManageStaffService {
    //[홈] 일별 근무 조회

    //[직원관리] 매장 직원 목록 조회
    @GET("${storePath}/{storeId}/employments")
    fun getStaffList(
        @Path("storeId") storeId: Int,
    ): Call<StaffList>

    //[직원관리] 직원 초대
    @POST("${storePath}/employments")
    fun inviteStaff(
        @Query("staffId") staffId: Int,
        @Query("storeId") storeId: Int
    ): Call<ResultResponse>

    //[직원관리] 매장 직원 정보 조회
    @GET("${employmentsPath}/{employmentId}")
    fun getStaffInfo(
        @Path("employmentId") employmentId: Int
    ): Call<ResponseGetStaffInfo>

    //[직원관리] 매장 직원 정보 수정
    @POST("${employmentsPath}/{employmentId}")
    fun modifyStaffInfo(
        @Path("employmentId") employmentId: Int,
        @Body params: UpdateRankAndWageRequest
    ): Call<ResultResponse>

    //[직원관리] 직원 삭제(보류)

    //[직원관리] 직원 검색
    @POST("${storePath}/staff-search")
    fun staffSearch(
        @Body params: HashMap<String, String>
    ): Call<ResponseFindStaff>

    companion object {
        const val storePath = "stores"
        const val employmentsPath = "stores/employments"
    }
}