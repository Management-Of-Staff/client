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
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import myapplication.second.workinghourmanagement.BuildConfig
import myapplication.second.workinghourmanagement.CustomDialog
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.RetrofitManager
import myapplication.second.workinghourmanagement.databinding.ActivityOwnerJoinInfoBinding
import myapplication.second.workinghourmanagement.dto.ResultBNumCheck
import myapplication.second.workinghourmanagement.dto.ResultResponse
import myapplication.second.workinghourmanagement.retrofit.OwnerService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OwnerJoinInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOwnerJoinInfoBinding
    private lateinit var service: OwnerService
    private lateinit var customDialog: CustomDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_owner_join_info)

        service = RetrofitManager.retrofit.create(OwnerService::class.java)
        bind()
    }

    private fun bind() {
        binding.toolbar.ivBack.setOnClickListener {
            finish()
        }
        binding.ownerJoinEditBusinessNum.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val bn = binding.ownerJoinEditBusinessNum.text.toString()
                binding.ownerJoinBtnBnCheck.isEnabled = checkBusinessNumValidation(bn)
            }
        })

        binding.ownerJoinBtnBnCheck.setOnClickListener {
            val retrofit_bnum = Retrofit.Builder()
                .baseUrl("https://api.odcloud.kr/api/nts-businessman/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val bn = HashMap<String, List<String>>()
            bn["b_no"] = listOf(binding.ownerJoinEditBusinessNum.text.toString())

            val serviceKey = BuildConfig.BNUM_KEY
            retrofit_bnum.create(OwnerService::class.java)
                .checkBNum(serviceKey = serviceKey, bn)
                .enqueue(object : Callback<ResultBNumCheck> {
                    override fun onResponse(
                        call: Call<ResultBNumCheck>, response: Response<ResultBNumCheck>
                    ) {
                        if (response.isSuccessful.not()) {
                            Log.d("보내는 지는데,,,", response.raw().toString())
                            return
                        }
                        val body = response.body()
                        if (body != null) {
                            val data = body.data!![0].tax_type
                            if (data == "부가가치세 일반과세자") {
                                errorMessageDialog("올바른 사업자등록번호입니다.")
                            } else {
                                errorMessageDialog(data)
                                binding.ownerJoinEditBusinessNum.text = null
                            }
                            Log.d("tax_type", body.data[0].tax_type)
                        }
                    }

                    override fun onFailure(call: Call<ResultBNumCheck>, t: Throwable) {
                        Log.d("getPosts fail", "[Fail]$t")
                    }
                })
        }

        binding.ownerJoinEditPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val pwCount = String.format(
                    getString(R.string.passwd_count),
                    binding.ownerJoinEditPassword.length()
                )
                binding.passwordCountText.text = pwCount
                val password = binding.ownerJoinEditPassword.text.toString()
                if (checkPasswordValidation(password)) {
                    binding.passwordValidator.setTextColor(resources.getColor(R.color.success1))
                } else {
                    binding.passwordValidator.setTextColor(resources.getColor(R.color.error1))
                }
            }
        })

        binding.passwordShow.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked) {
                binding.ownerJoinEditPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
            } else {
                binding.ownerJoinEditPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
            }
            binding.ownerJoinEditPassword.setSelection(binding.ownerJoinEditPassword.text.length)
        }

        binding.ownerJoinEditPasswordConfirm.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val pwCount = String.format(
                    getString(R.string.passwd_count),
                    binding.ownerJoinEditPassword.length()
                )
                binding.passwordConfirmCountText.text = pwCount
                if (binding.ownerJoinEditPassword.text.toString() != binding.ownerJoinEditPasswordConfirm.text.toString()) {
                    binding.passwordConfirmValidator.setText(R.string.password_disMatch)
                    binding.passwordConfirmValidator.setTextColor(resources.getColor(R.color.error1))
                    binding.ownerJoinBtnJoin.isEnabled = false
                } else {
                    binding.passwordConfirmValidator.setText(R.string.password_match)
                    binding.passwordConfirmValidator.setTextColor(resources.getColor(R.color.success1))
                    //todo 모든 정보 입력했는지 확인한 후 enabled = true 설정!
                    binding.ownerJoinBtnJoin.isEnabled = true
                }
            }
        })

        binding.passwordConfirmShow.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked) {
                binding.ownerJoinEditPasswordConfirm.transformationMethod =
                    PasswordTransformationMethod.getInstance()
            } else {
                binding.ownerJoinEditPasswordConfirm.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
            }
            binding.ownerJoinEditPasswordConfirm.setSelection(binding.ownerJoinEditPasswordConfirm.text.length)
        }

        binding.ownerJoinBtnJoin.setOnClickListener {
            // todo 모든 필드가 입력되었는지 체크하기
            registerUser()
        }
    }

    private fun joinSuccess() {
        val intent = Intent(this, OwnerJoinCompleteActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }

    private fun errorMessageDialog(message: String) {
        customDialog = CustomDialog(this, message)
        customDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        customDialog.show()
        customDialog.shutdownClick.setOnClickListener { customDialog.dismiss() }
    }

    private fun registerUser() {
        val myIntent = intent
        val phone = myIntent.getStringExtra("phone")!!
        val userInfo = HashMap<String, String>()
        userInfo["name"] = binding.ownerJoinEditOwnerName.text.toString()
        userInfo["password"] = binding.ownerJoinEditPassword.text.toString()
        userInfo["phone"] = phone

        service.signUpOwner(userInfo)
            .enqueue(object : Callback<ResultResponse> {
                override fun onResponse(
                    call: Call<ResultResponse>,
                    response: Response<ResultResponse>
                ) {
                    if (response.isSuccessful.not()) {
                        if (response.code() == 500) errorMessageDialog("서버 내부에 문제가 발생했습니다.\n잠시후 다시시도 해주세요.")
                        return
                    }
                    val body = response.body()
                    if (body != null) {
                        Log.d("statusCode", body.statusCode.toString())
                        Log.d("message", body.message)

                        if (body.statusCode == 200) joinSuccess()
                        else if (body.statusCode == 400) errorMessageDialog(body.message)
                    }
                }

                override fun onFailure(call: Call<ResultResponse>, t: Throwable) {
                    Log.d("[Retrofit]", "[registerOwner Fail]$t")
                }
            })
    }

    private fun checkPasswordValidation(pw: String): Boolean {
        return pw.matches("^(?=.*[a-zA-Z])(?=.*[0-9]).{8,20}$".toRegex())
    }

    private fun checkBusinessNumValidation(bn: String): Boolean {
        return if (bn.length == 10) {
            val keyArr = arrayOf(1, 3, 7, 1, 3, 7, 1, 3, 5)
            var check = 0

            keyArr.forEachIndexed { index, i ->
                check += i * bn[index].digitToInt()
            }
            check += (bn[8].digitToInt() * keyArr[8]) / 10

            bn[9].digitToInt() == (10 - (check % 10))
        } else false
    }
}