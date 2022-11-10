package myapplication.second.workinghourmanagement

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import myapplication.second.workinghourmanagement.databinding.CommonLoginActivityBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: CommonLoginActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.common_login_activity)

        binding.textJoin.setOnClickListener{
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }

    }
}