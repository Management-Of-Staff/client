package myapplication.second.workinghourmanagement

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = MyApplication.prefs.getString("accessToken")
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .build()
        val response = chain.proceed(request)
        when(response.code){
            400 -> {

            }
            401 -> {
               val refreshToken = MyApplication.prefs.getString("refreshToken")
                if(refreshToken != ""){
                    val newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer $refreshToken")
                        .build()

                    return chain.proceed(newRequest)
                }
            }
        }

        return response
    }
}