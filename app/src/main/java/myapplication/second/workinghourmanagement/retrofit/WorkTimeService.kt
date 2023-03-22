package myapplication.second.workinghourmanagement.retrofit

import myapplication.second.workinghourmanagement.dto.ResultResponse
import myapplication.second.workinghourmanagement.dto.workTime.WorkAddRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface WorkTimeService {
    //[직원 관리] 근무 수정

    //[직원 관리] 근무 추가
    @POST("employments/{employmentId}/schedule")
    fun addWorkTime(
        @Path("employmentId") employmentId: Int,
        @Body params: WorkAddRequest
    ): Call<ResultResponse>

    //[직원 관리] 근무 삭제

}