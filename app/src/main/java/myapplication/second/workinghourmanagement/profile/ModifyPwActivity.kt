package myapplication.second.workinghourmanagement.profile

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import myapplication.second.workinghourmanagement.*
import myapplication.second.workinghourmanagement.databinding.ActivityModifyPasswdBinding
import myapplication.second.workinghourmanagement.dto.ResultResponse
import myapplication.second.workinghourmanagement.member.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModifyPwActivity : AppCompatActivity() {
    private lateinit var customDialog: CustomDialog
    private lateinit var service: RetrofitService
    private lateinit var binding: ActivityModifyPasswdBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_modify_passwd)
        service = RetrofitManager.retrofit.create(RetrofitService::class.java)

        initActionbar()
        bind()
    }

    private fun bind() {
        binding.editNewPw.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.editNewPwConfirm.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                binding.btnChangePw.isEnabled = checkPasswordValidation(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.btnChangePw.setOnClickListener {
            changePassword()
        }
    }

    private fun changePassword() {
        val token = "Bearer " + MyApplication.prefs.getString("accessToken")
        val body = HashMap<String, String>()
        body["newPassword"] = binding.editNewPw.text.toString()
        body["oldPassword"] = intent.getStringExtra("currentPassword")!!
        service.updatePassword(token, body).enqueue(object : Callback<ResultResponse> {
            override fun onResponse(
                call: Call<ResultResponse>,
                response: Response<ResultResponse>
            ) {
                //todo 성공시 로그아웃처리(request, token 삭제), 로그인 화면으로 intent
                messageDialog(response.body()!!.message)
            }

            override fun onFailure(call: Call<ResultResponse>, t: Throwable) {
                Log.e("changPassword fail", "[Fail]$t")
            }
        })
    }

    private fun messageDialog(msg: String) {
        customDialog = CustomDialog(this, msg)
        customDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        customDialog.show()
        customDialog.shutdownClick.setOnClickListener {
            customDialog.dismiss()
            intentPage(MainActivity::class.java)
        }
    }

    private fun intentPage(where: Class<*>) {
        val intent = Intent(this, where)
        startActivity(intent)
        finishAffinity()
    }

    private fun initActionbar() {
        setSupportActionBar(binding.toolbar)
        val actionBar: ActionBar = supportActionBar!!
        actionBar.setDisplayShowTitleEnabled(false)
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkPasswordValidation(pw: String): Boolean {
        return pw.matches("^(?=.*[a-zA-Z])(?=.*[0-9]).{8,20}$".toRegex())
    }
}