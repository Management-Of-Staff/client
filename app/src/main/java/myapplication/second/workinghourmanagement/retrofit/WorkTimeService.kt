package myapplication.second.workinghourmanagement.retrofit

import myapplication.second.workinghourmanagement.dto.workTime.*
import retrofit2.Call
import retrofit2.http.*

interface WorkTimeService {
    //[직원 관리] 근무 수정
    @PUT("employment/{employmentId}/schedule")
    fun updateWorkTime(
        @Path("employmentId") employmentId: Int,
        @Body params: WorkUpdateRequest
    ): Call<WorkUpdateResponse>

    //[직원 관리] 근무 추가
    @POST("employments/{employmentId}/schedule")
    fun addWorkTime(
        @Path("employmentId") employmentId: Int,
        @Body params: WorkAddRequest
    ): Call<WorkAddResponse>

    //[직원 관리] 근무 삭제
    @DELETE("employments/{employmentId}/schedule")
    fun deleteWorkTime(
        @Path("employmentId") employmentId: Int,
        @Body params: WorkDeleteRequest
    ): Call<WorkDeleteResponse>
}