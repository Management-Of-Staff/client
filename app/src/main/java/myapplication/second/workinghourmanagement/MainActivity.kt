package myapplication.second.workinghourmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import myapplication.second.workinghourmanagement.databinding.ActivityCommonLoginBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommonLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common_login)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_common_login)

        binding.textJoin.setOnClickListener {
            val intent = Intent(this, OwnerTosActivity::class.java)
            startActivity(intent)
        }
    }
}