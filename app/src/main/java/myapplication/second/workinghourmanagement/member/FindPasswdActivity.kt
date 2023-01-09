package myapplication.second.workinghourmanagement.member

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.databinding.ActivityCommonFindpwBinding

class FindPasswdActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommonFindpwBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_common_findpw)

        bind()
        initActionBar()
    }

    private fun bind() {
        binding.findPwBtnAuth.setOnClickListener {
            //todo 인증코드 발송
        }
        binding.findPwBtnCheck.setOnClickListener {
            //todo 인증코드일치하면 intent -> resetPasswd
            val intent = Intent(this, ResetPasswdActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initActionBar() {
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