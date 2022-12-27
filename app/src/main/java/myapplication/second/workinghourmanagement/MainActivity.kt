package myapplication.second.workinghourmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import myapplication.second.workinghourmanagement.databinding.CommonLoginActivityBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: CommonLoginActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.common_login_activity)
        binding = DataBindingUtil.setContentView(this, R.layout.common_login_activity)

        binding.textJoin.setOnClickListener{
            val intent = Intent(this, OwnerTosActivity::class.java)
            startActivity(intent)
        }
    }
}