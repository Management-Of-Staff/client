package myapplication.second.workinghourmanagement.member

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import myapplication.second.workinghourmanagement.*
import myapplication.second.workinghourmanagement.databinding.ActivityCommonLoginBinding
import myapplication.second.workinghourmanagement.dto.ResultToken
import myapplication.second.workinghourmanagement.home.OwnerHomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommonLoginBinding
    private lateinit var service: RetrofitService
    private lateinit var customDialog: CustomDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_common_login)

        // todo 지금은 prefs에 토큰값있으면 그냥 자동 로그인 -> 토큰 만료되었는지 확인하는거 추가하기
        if(MyApplication.prefs.getString("accessToken").isNotBlank()){
            intentPage(OwnerHomeActivity::class.java, null)
            finish()
        }

        service = RetrofitManager.loginRetrofit.create(RetrofitService::class.java)
        bind()
    }

    private fun bind() {
        // 비밀번호 관련 아이콘 visible
        binding.editPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.editPassword.length() != 0) {
                    binding.passwordClear.visibility = View.VISIBLE
                    binding.passwordVisibleCheckbox.visibility = View.VISIBLE
                } else {
                    binding.passwordClear.visibility = View.GONE
                    binding.passwordVisibleCheckbox.visibility = View.GONE
                }
            }
        })

        // 비밀번호 입력 초기화
        binding.passwordClear.setOnClickListener {
            binding.passwordVisibleCheckbox.isChecked = false
            binding.editPassword.text = null
        }

        // 비밀번호 숨기기/보이기
        binding.passwordVisibleCheckbox.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked) {
                binding.passwordVisibleCheckbox.setButtonDrawable(R.drawable.ic_password_visible_off)
                binding.editPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
            } else {
                binding.passwordVisibleCheckbox.setButtonDrawable(R.drawable.ic_password_visible)
                binding.editPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
            }
            binding.editPassword.setSelection(binding.editPassword.text.length)
        }

        // 로그인
        binding.buttonLogin.setOnClickListener {
            //todo 로그인시 직원/오너 일치 확인
            login(binding.radioGroupRole.checkedRadioButtonId)
        }

        // 회원가입
        binding.textJoin.setOnClickListener {
            if (binding.radioGroupRole.checkedRadioButtonId == R.id.radio_button_staff)
                intentPage(StaffTosActivity::class.java, null)
            else intentPage(OwnerTosActivity::class.java, null)
        }

        // 비밀번호 찾기
        binding.textFindPassword.setOnClickListener {
            intentPage(PhoneAuthActivity::class.java, "find")
        }
    }

    private fun intentPage(where: Class<*>, state: String?) {
        val intent = Intent(this, where)
        if (!state.isNullOrBlank()) intent.putExtra("state", state)
        startActivity(intent)
    }

    private fun login(role: Int) {
        val loginRequestParams = HashMap<String, String>()
        loginRequestParams["phoneNum"] = binding.editPhone.text.toString()
        loginRequestParams["password"] = binding.editPassword.text.toString()

        service.signInOwner(loginRequestParams).enqueue(object : Callback<ResultToken> {
            override fun onResponse(call: Call<ResultToken>, response: Response<ResultToken>) {
                if (response.isSuccessful.not()) {
                    if (response.code() == 500) errorMessageDialog("서버 내부에 문제가 발생했습니다.\n잠시후 다시시도 해주세요.")
                    return
                }

                val body = response.body()
                if (body != null) {
                    if (!body.accessToken.isNullOrBlank() && !body.refreshToken.isNullOrBlank()) {
                        //todo 토큰 저장 후 intent
                        MyApplication.prefs.setString("accessToken", body.accessToken)
                        MyApplication.prefs.setString("refreshToken", body.refreshToken)
                        if (role == R.id.radio_button_owner) loginOwnerSuccess()
                        else loginStaffSuccess()
                    } else {
//                        Log.d("response.message", body.message!!)
                        errorMessageDialog(body.message!!)
                    }
                }
            }

            override fun onFailure(call: Call<ResultToken>, t: Throwable) {
                Log.d("getPosts fail", "[Fail]$t")
            }
        })
    }

    private fun loginStaffSuccess() {
        //todo StaffHomeActivity::class.java 로 바꿔야 함
        intentPage(OwnerHomeActivity::class.java, null)
//        startActivity(Intent(this, OwnerHomeActivity::class.java))
        finish()
    }

    fun errorMessageDialog(message: String) {
        customDialog = CustomDialog(this, message)
        customDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        customDialog.show()
        customDialog.shutdownClick.setOnClickListener { customDialog.dismiss() }
    }

    fun loginOwnerSuccess() {
        intentPage(OwnerHomeActivity::class.java, null)
        finish()
    }
}