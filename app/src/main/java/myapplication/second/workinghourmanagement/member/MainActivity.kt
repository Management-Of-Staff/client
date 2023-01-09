package myapplication.second.workinghourmanagement.member

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import myapplication.second.workinghourmanagement.CustomDialog
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
    private lateinit var service: RetrofitService
    private lateinit var customDialog: CustomDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_common_login)

        service = RetrofitManager.retrofit.create(RetrofitService::class.java)
        bind()
    }

    private fun bind() {
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
                    return
                }
                val body = response.body()
                if (body != null) {
                    if (!body.accessToken.isNullOrBlank() && !body.refreshToken.isNullOrBlank()) {
                        //todo 토큰 저장 후 intent
                    } else {
                        Log.d("response.message", body.message!!)
                        errorMessageDialog(body.message)
                    }
                }
            }

            override fun onFailure(call: Call<ResultLogin>, t: Throwable) {
                Log.d("getPosts fail", "[Fail]$t")
            }
        })
    }

    fun errorMessageDialog(message: String) {
        customDialog = CustomDialog(this, message)
        customDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        customDialog.show()
        customDialog.shutdownClick.setOnClickListener { customDialog.dismiss() }
    }
}