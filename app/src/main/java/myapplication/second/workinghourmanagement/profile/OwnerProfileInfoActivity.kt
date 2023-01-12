package myapplication.second.workinghourmanagement.profile

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.databinding.ActivityOwnerProfileInfoBinding
import myapplication.second.workinghourmanagement.dto.UserParcelable

class OwnerProfileInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOwnerProfileInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_owner_profile_info)

        bind()
        initProfile()
        initActionbar()
    }

    private fun bind() {
        binding.btnSettingBirth.setOnClickListener {

        }
        binding.btnChangePhone.setOnClickListener {

        }
        binding.btnChangePasswd.setOnClickListener {
            intentPage(CheckCurrentPwActivity::class.java)
        }
        binding.btnSaveProfile.setOnClickListener {

        }
    }

    private fun intentPage(where: Class<*>) {
        val intent = Intent(this, where)
        startActivity(intent)
    }

    private fun initProfile() {
        val myIntent = intent
        val user = myIntent.getParcelableExtra<UserParcelable>("userInfo")!!

        binding.name.text = user.name
        binding.phone.text = user.phone

        val birth = user.birthDate
        val email = user.email

        if (birth.isNullOrEmpty()) binding.birth.text = "생일을 입력해주세요"
        else binding.birth.text =
            String.format(getString(R.string.birthDate), birth[0], birth[1], birth[2])

        if (email.isNullOrBlank()) binding.email.text = "이메일 주소를 입력해주세요"
        else binding.email.text = email
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