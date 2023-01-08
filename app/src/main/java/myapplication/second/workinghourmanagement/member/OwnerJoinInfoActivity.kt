package myapplication.second.workinghourmanagement.member

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
import myapplication.second.workinghourmanagement.databinding.ActivityOwnerJoinInfoBinding
import myapplication.second.workinghourmanagement.dto.ResultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OwnerJoinInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOwnerJoinInfoBinding
    private lateinit var service: RetrofitService
    private lateinit var customDialog: CustomDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_owner_join_info)

        service = RetrofitManager.retrofit.create(RetrofitService::class.java)
        bind()

        setSupportActionBar(binding.toolbar)
        val actionBar: ActionBar = supportActionBar!!
        actionBar.setDisplayShowTitleEnabled(false)
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    private fun bind() {
        binding.ownerJoinEditBusinessNum.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val bn = binding.ownerJoinEditBusinessNum.text.toString()
                binding.ownerJoinBtnBnCheck.isEnabled = checkBusinessNumValidation(bn)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        binding.ownerJoinBtnBnCheck.setOnClickListener {
            customDialog = CustomDialog(this, getString(R.string.bn_success))
            customDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            customDialog.show()
            customDialog.shutdownClick.setOnClickListener { customDialog.dismiss() }
        }

        binding.ownerJoinBtnJoin.setOnClickListener {
            registerUser()
        }

        binding.ownerJoinEditPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val password = binding.ownerJoinEditPassword.text.toString()
                if (checkPasswordValidation(password)) {
                    binding.passwordValidator.setTextColor(resources.getColor(R.color.success1))
                } else {
                    binding.passwordValidator.setTextColor(resources.getColor(R.color.error1))
                }
            }
        })

        binding.ownerJoinEditPasswordConfirm.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
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
    }

    private fun joinSuccess() {
        val intent = Intent(this, OwnerJoinCompleteActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }

    private fun registerUser() {
        val myIntent = intent
        val phone = myIntent.getStringExtra("phone")!!
        val uuid = myIntent.getStringExtra("uuid")!!
//        val user = User(
//            uuid,
//            binding.ownerJoinEditOwnerName.text.toString(),
//            "01000000003",
//            binding.ownerJoinEditPassword.text.toString(),
//            "OWNER"
//        )
        val userInfo = HashMap<String, String>()
//        userInfo["uuid"] = uuid
        userInfo["name"] = binding.ownerJoinEditOwnerName.text.toString()
        userInfo["phone"] = "01000000004"
        //todo 유저 입력으로 변경하기(지금은 테스트용)
//        userInfo["phone"] = phone
        userInfo["password"] = binding.ownerJoinEditPassword.text.toString()
        userInfo["role"] = "OWNER"

//        service.registerOwner(user.name, user.phone, user.password, user.role)
        service.registerOwner(userInfo)
            .enqueue(object : Callback<ResultResponse> {
                override fun onResponse(
                    call: Call<ResultResponse>,
                    response: Response<ResultResponse>
                ) {
                    if (response.isSuccessful.not()) {
//                        Log.d("보내긴함 근데 실패,,,", "${response.body()}")
//                        Log.d("response는 뭐가 나와?", "${response.raw()}")
                        return
                    }
                    val body = response.body()
                    if (body != null) {
                        Log.d("data", body.data.toString())
                        Log.d("statusCode", body.statusCode.toString())
                        Log.d("message", body.message)

                        if (body.statusCode == 200) joinSuccess()
                    }
//                    response.body()?.let {
//                        Log.d("getPosts success", "\n$it")
//                    }
                }

                override fun onFailure(call: Call<ResultResponse>, t: Throwable) {
                    Log.d("getPosts fail", "[Fail]$t")
                }
            })
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