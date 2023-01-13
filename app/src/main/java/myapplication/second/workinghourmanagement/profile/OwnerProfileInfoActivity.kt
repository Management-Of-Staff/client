package myapplication.second.workinghourmanagement.profile

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import myapplication.second.workinghourmanagement.MyApplication
import myapplication.second.workinghourmanagement.RetrofitService
import myapplication.second.workinghourmanagement.*
import myapplication.second.workinghourmanagement.databinding.ActivityOwnerProfileInfoBinding
import myapplication.second.workinghourmanagement.dto.ResultResponse
import myapplication.second.workinghourmanagement.dto.UserParcelable
import myapplication.second.workinghourmanagement.member.PhoneAuthActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OwnerProfileInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOwnerProfileInfoBinding
    private lateinit var service: RetrofitService
    private val token = "Bearer " + MyApplication.prefs.getString("accessToken")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_owner_profile_info)
        service = RetrofitManager.retrofit.create(RetrofitService::class.java)

        bind()
        initProfile()
        initActionbar()
    }

    private fun bind() {
        binding.email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!checkEmail(s.toString())) {
                    binding.email.setTextColor(resources.getColor(R.color.error1))
                } else {
                    binding.email.setTextColor(resources.getColor(R.color.success1))
                }
            }
        })
        binding.btnSettingBirth.setOnClickListener {
//            val dialog = BottomSheetDialog(this)
//            dialog.setContentView(R.layout.dialog_fragment_bottom_sheet_birth)
//            val btn = dialog.findViewById<Button>(R.id.btn_confirm)
//            val birth = dialog.findViewById<Spinner>(R.id.datePicker)
//
////            btn?.setOnClickListener {
////                binding.birth.text = birth.year.toString() + "/" + (birth.month + 1).toString() + "/" + birth.dayOfMonth.toString()
////                Toast.makeText(this, "버튼 클릭", Toast.LENGTH_SHORT).show()
////                dialog.dismiss()
////            }
//            dialog.show()

            val newFragment = SetBirthDialog()
            newFragment.show(supportFragmentManager, "datePicker")
        }
        binding.btnChangePhone.setOnClickListener {
            intentPage(PhoneAuthActivity::class.java, "update")
        }
        binding.btnChangePasswd.setOnClickListener {
            intentPage(CheckCurrentPwActivity::class.java, null)
        }
        binding.btnSaveProfile.setOnClickListener {
            val birth = binding.birth.text.toString().split(".")
            val profile = HashMap<String, String>()
            profile["email"] = binding.email.text.toString()
            profile["birthDate"] = birth[0] + "-" + birth[1] + "-" + birth[2]
            updateProfile(token, profile)
        }
    }

    private fun updateProfile(token: String, profile: HashMap<String, String>) {
        service.updateProfile(token, profile).enqueue(object : Callback<ResultResponse> {
            override fun onResponse(
                call: Call<ResultResponse>, response: Response<ResultResponse>
            ) {
                if (response.body()!!.statusCode == 200) {
                    finish()
                    //todo 뷰 다시그리기(initProfile 에서 정보조회 콜하는걸로 바꿀까?)
                    Log.d("tag", "성공")
                }
            }

            override fun onFailure(call: Call<ResultResponse>, t: Throwable) {
                Log.e("updateProfile call", "실패: $t")
            }
        })
    }

    fun getDate(year: Int, month: Int, day: Int){
        val birth = String.format(getString(R.string.birth_format), year, month+1, day)
        binding.birth.text = birth
    }
    private fun intentPage(where: Class<*>, state: String?) {
        val intent = Intent(this, where)
        if(!state.isNullOrBlank()){
            intent.putExtra("state", state)
        }
        startActivity(intent)
    }

    private fun initProfile() {
        val myIntent = intent
        val user = myIntent.getParcelableExtra<UserParcelable>("userInfo")!!

        binding.name.text = user.name
        binding.phone.text = user.phone

        val birth = user.birthDate
        val email = user.email

        if (birth.isNullOrEmpty()) binding.birth.text = null
        else binding.birth.text =
            String.format(getString(R.string.birthDate), birth[0], birth[1], birth[2])

        if (email.isNullOrBlank()) binding.email.text = null
        else binding.email.setText(email)
    }

    private fun initActionbar() {
        setSupportActionBar(binding.toolbar)
        val actionBar: ActionBar = supportActionBar!!
        actionBar.setDisplayShowTitleEnabled(false)
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    fun checkEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches() // 서로 패턴이 맞닝?
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