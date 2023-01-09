package myapplication.second.workinghourmanagement.member

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.RetrofitManager
import myapplication.second.workinghourmanagement.RetrofitService
import myapplication.second.workinghourmanagement.databinding.ActivityCommonLoginBinding
import myapplication.second.workinghourmanagement.dto.ResultLogin
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
        binding.textFindPassword.setOnClickListener {
            val intent = Intent(this, FindPasswdActivity::class.java)
            startActivity(intent)
        }

        binding.buttonLogin.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val loginRequestParams = HashMap<String, String>()
        loginRequestParams["phone"] = binding.editPhone.text.toString()
        loginRequestParams["password"] = binding.editPassword.text.toString()

        service.loginOwner(loginRequestParams).enqueue(object : Callback<ResultLogin> {
            override fun onResponse(call: Call<ResultLogin>, response: Response<ResultLogin>) {
                if (response.isSuccessful.not()) {
//                    Log.d("ㄱ half success", "${response.raw()}")
                    return
                }
                val body = response.body()
                if (body != null) {
                    Log.d("accessToken", body.accessToken)
                    Log.d("refreshToken", body.refreshToken)
                }
            }

            override fun onFailure(call: Call<ResultLogin>, t: Throwable) {
                Log.d("getPosts fail", "[Fail]$t")
            }
        })
    }
}