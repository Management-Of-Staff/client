package myapplication.second.workinghourmanagement.profile

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import myapplication.second.workinghourmanagement.*
import myapplication.second.workinghourmanagement.databinding.ActivityCheckCurrentPasswdBinding
import myapplication.second.workinghourmanagement.dto.ResultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckCurrentPwActivity : AppCompatActivity() {
    private lateinit var service: RetrofitService
    private lateinit var customDialog: CustomDialog
    private lateinit var binding: ActivityCheckCurrentPasswdBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_check_current_passwd)
        service = RetrofitManager.retrofit.create(RetrofitService::class.java)

        bind()
        initActionbar()
    }

    private fun bind() {
        binding.buttonCheckCurrentPw.setOnClickListener {
            checkPassword()
        }
    }

    private fun checkPassword() {
        val currentPassword = HashMap<String, String>()
        currentPassword["password"] = binding.editCurrentPassword.text.toString()
        val tok = "Bearer " + MyApplication.prefs.getString("accessToken")
        service.checkPassword(
            tok, currentPassword
        ).enqueue(object : Callback<ResultResponse> {
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
        intent.putExtra("currentPassword", binding.editCurrentPassword.text.toString())
        startActivity(intent)
    }

    fun errorMessageDialog(message: String) {
        customDialog = CustomDialog(this, message)
        customDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        customDialog.show()
        customDialog.shutdownClick.setOnClickListener { customDialog.dismiss() }
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
}