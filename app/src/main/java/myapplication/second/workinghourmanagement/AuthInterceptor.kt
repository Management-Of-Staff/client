package myapplication.second.workinghourmanagement

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONObject

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = MyApplication.prefs.getString("accessToken")
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .build()
        val response = chain.proceed(request)

        val responseJson = response.extractResponseJson()
        val bodyCode = responseJson.getString("status")
        Log.d("bodyCode 출력", bodyCode)

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