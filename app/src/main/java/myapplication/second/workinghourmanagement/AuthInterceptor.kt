package myapplication.second.workinghourmanagement

import android.util.Log
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import myapplication.second.workinghourmanagement.dto.ResultToken
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import retrofit2.Call
import retrofit2.Callback

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = MyApplication.prefs.getString("accessToken")
        Log.d("$TAG accessToken", accessToken)
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .build()
        val response = chain.proceed(request)

        when (response.code) {
            403 -> {
                response.close()
                val newRequest: Request = runBlocking {
                    launch { reissue() }
                    // todo 동기처리!! reissue 완료된 후에 newRequest 날리도록!
                    return@runBlocking newRequest(chain)
                }
                return chain.proceed(newRequest)
            }
        }

        return response
    }

    private fun newRequest(chain: Interceptor.Chain): Request {
        val newAccessToken = MyApplication.prefs.getString("accessToken")
        Log.d("$TAG - newAccessToken", newAccessToken)
        return chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $newAccessToken")
            .build()
    }

    private fun reissue() {
        RetrofitManager.reissueRetrofit.create(RetrofitService::class.java)
            .reissue().enqueue(object : Callback<ResultToken> {
                override fun onResponse(
                    call: Call<ResultToken>,
                    response: retrofit2.Response<ResultToken>
                ) {}
                override fun onFailure(call: Call<ResultToken>, t: Throwable) {
                    Log.e("changPassword fail", "[Fail]$t")
                }
            })
    }

    companion object {
        private const val TAG = "Auth"
    }
}