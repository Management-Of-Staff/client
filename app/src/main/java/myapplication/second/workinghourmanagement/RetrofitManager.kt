package myapplication.second.workinghourmanagement

import myapplication.second.workinghourmanagement.BuildConfig.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitManager {
    private val BASE_URL: String = BuildConfig.BASE_URL

    companion object{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
//            .baseUrl("https://jsonplaceholder.typicode.com")  //test용
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}