package myapplication.second.workinghourmanagement

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONObject

class ReissueInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val refreshToken = MyApplication.prefs.getString("refreshToken")
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $refreshToken")
            .build()
        val response = chain.proceed(request)

        when (response.code) {
            200 -> {    //token 재발급 성공 -> token 저장하고, 재요청
                val responseJson = response.extractResponseJson()
                val newAccessToken = responseJson.getString("accessToken")
                val newRefreshToken = responseJson.getString("refreshToken")

                Log.d("$TAG newAccessToken", newAccessToken)
                Log.d("$TAG newRefreshToken", newRefreshToken)
                MyApplication.prefs.setString("accessToken", newAccessToken)
                MyApplication.prefs.setString("refreshToken", newRefreshToken)
            }
            403 -> {    //refreshToken 만료 -> token 초기화, 로그인 페이지로 이동
                MyApplication.prefs.setString("accessToken", "")
                MyApplication.prefs.setString("refreshToken", "")
                //todo 로그인화면으로 이동
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
        private const val TAG = "Reissue"
    }
}