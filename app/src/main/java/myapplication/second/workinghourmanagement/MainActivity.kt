package myapplication.second.workinghourmanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
//    private lateinit var binding: CommonLoginActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.common_login_activity)
//        binding = DataBindingUtil.setContentView(this, R.layout.common_login_activity)

//        binding.textJoin.setOnClickListener{
//            val intent = Intent(this, JoinActivity::class.java)
//            startActivity(intent)
//        }

    }
}