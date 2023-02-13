package myapplication.second.workinghourmanagement

import android.util.Log
import kotlinx.coroutines.runBlocking
import myapplication.second.workinghourmanagement.dto.ResultToken
import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = MyApplication.prefs.getString("accessToken")
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .build()
        val response = chain.proceed(request)

        when (response.code) {
            403 -> {
                val responseJson = response.extractResponseJson()
                val bodyCode = responseJson.getString("status")
                // 411 : accessToken 만료
                // todo 서버 배포되면 에러코드 수정하기(403 -> 411)
                if (bodyCode == "403") {
                    // todo 토큰재발급 후 저장(동기처리)

                    val refreshToken = "Bearer " + MyApplication.prefs.getString("refreshToken")
                    RetrofitManager.loginRetrofit.create(RetrofitService::class.java)
                        .reissue(refreshToken).enqueue(object : Callback<ResultToken> {
                            override fun onResponse(
                                call: Call<ResultToken>,
                                response: retrofit2.Response<ResultToken>
                            ) {
                                MyApplication.prefs.setString(
                                    "accessToken",
                                    response.body()!!.accessToken.toString()
                                )
                                MyApplication.prefs.setString(
                                    "refreshToken",
                                    response.body()!!.refreshToken.toString()
                                )
                            }

                            override fun onFailure(call: Call<ResultToken>, t: Throwable) {
                                Log.e("changPassword fail", "[Fail]$t")
                            }
                        })

                    // todo 동기처리하기 앞에서 저장이 완료된 후에 request 날리도록!
                    val newAccessToken = MyApplication.prefs.getString("accessToken")
                    Log.d("newAccessToken", newAccessToken)
                    val newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer $newAccessToken")
                        .build()
                    return chain.proceed(newRequest)
                }
                // 412 : refreshToken 만료 -> 토큰 초기화하고, 로그인 페이지로 이동
                else if (bodyCode == "412") {
                    MyApplication.prefs.setString("accessToken", "")
                    MyApplication.prefs.setString("refreshToken", "")
                }
            }
        }
        return response
    }

    private fun Response.extractResponseJson(): JSONObject {
        val jsonString = this.peekBody(Long.MAX_VALUE).string()
        Log.d("im jsonString :)", jsonString)
        return try {
            JSONObject(jsonString)
        } catch (e: Exception) {
            e.printStackTrace()
            JSONObject(EMPTY_JSON)
        }
    }

    companion object {
        private const val EMPTY_JSON = "{}"
    }
}