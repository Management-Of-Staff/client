package myapplication.second.workinghourmanagement.member

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import myapplication.second.workinghourmanagement.PostResult
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.RetrofitManager
import myapplication.second.workinghourmanagement.RetrofitService
import myapplication.second.workinghourmanagement.databinding.ActivityCommonLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommonLoginBinding
    lateinit var service: RetrofitService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_common_login)

        service = RetrofitManager.retrofit.create(RetrofitService::class.java)

        binding.textJoin.setOnClickListener {
            val intent = Intent(this, OwnerTosActivity::class.java)
            startActivity(intent)
        }

        binding.buttonLogin.setOnClickListener {
            getPosts()
        }
    }

    private fun getPosts() {
        service.getPosts("1").enqueue(object: Callback<PostResult>{
            override fun onResponse(call: Call<PostResult>, response: Response<PostResult>) {
                if(response.isSuccessful.not()){
                    return
                }
                response.body()?.let {
                    Log.d("getPosts success", "\n$it")
                }
            }

            override fun onFailure(call: Call<PostResult>, t: Throwable) {
                Log.d("getPosts fail", "[Fail]$t")
            }
        })
    }
}