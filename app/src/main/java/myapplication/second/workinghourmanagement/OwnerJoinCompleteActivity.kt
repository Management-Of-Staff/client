package myapplication.second.workinghourmanagement

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import myapplication.second.workinghourmanagement.databinding.ActivityOwnerJoinCompleteBinding

class OwnerJoinCompleteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOwnerJoinCompleteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_owner_join_complete)

        binding.btnOk.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }
}