package myapplication.second.workinghourmanagement.profile

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import myapplication.second.workinghourmanagement.CustomDialog
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.RetrofitManager
import myapplication.second.workinghourmanagement.databinding.ActivityCheckCurrentPasswdBinding
import myapplication.second.workinghourmanagement.dto.ResultResponse
import myapplication.second.workinghourmanagement.retrofit.OwnerService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckCurrentPwActivity : AppCompatActivity() {
    private lateinit var service: OwnerService
    private lateinit var customDialog: CustomDialog
    private lateinit var binding: ActivityCheckCurrentPasswdBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_check_current_passwd)
        service = RetrofitManager.retrofit.create(OwnerService::class.java)

        bind()
    }

    private fun bind() {
        binding.toolbar.ivBack.setOnClickListener {
            finish()
        }
        binding.buttonCheckCurrentPw.setOnClickListener {
            checkPassword()
        }
    }

    private fun checkPassword() {
        val currentPassword = HashMap<String, String>()
        currentPassword["password"] = binding.editCurrentPassword.text.toString()
        service.passwordCheckOwner(currentPassword).enqueue(object : Callback<ResultResponse> {
            override fun onResponse(
                call: Call<ResultResponse>,
                response: Response<ResultResponse>
            ) {
                val body = response.body()
                if (body != null) {
                    if (body.statusCode == 200) {
                        intentPage(ModifyPwActivity::class.java)
                    } else {
                        errorMessageDialog(body.message)
                        binding.editCurrentPassword.text = null
                    }
                }
            }

            override fun onFailure(call: Call<ResultResponse>, t: Throwable) {
                Log.e("checkPassword fail", "[Fail]$t")
            }
        })
    }

    private fun intentPage(where: Class<*>) {
        val intent = Intent(this, where)
        startActivity(intent)
    }

    fun errorMessageDialog(message: String) {
        customDialog = CustomDialog(this, message)
        customDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        customDialog.show()
        customDialog.shutdownClick.setOnClickListener { customDialog.dismiss() }
    }
}