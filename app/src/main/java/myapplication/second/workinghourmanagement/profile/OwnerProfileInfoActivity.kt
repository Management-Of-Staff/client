package myapplication.second.workinghourmanagement.profile

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.databinding.ActivityOwnerProfileInfoBinding

class OwnerProfileInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOwnerProfileInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_owner_profile_info)

        val myIntent = intent
        binding.name.text = myIntent.getStringExtra("name")
        binding.phone.text = myIntent.getStringExtra("phone")

        val birth = myIntent.getStringExtra("birth")
        val email = myIntent.getStringExtra("email")
        Log.d("birth", "${birth}, $email")

        if (birth.isNullOrBlank()) binding.birth.text = "생일을 입력해주세요"
        else binding.birth.text = birth
        if (email.isNullOrBlank()) binding.email.text = "이메일 주소를 입력해주세요"
        else binding.email.text = email

        initActionbar()
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