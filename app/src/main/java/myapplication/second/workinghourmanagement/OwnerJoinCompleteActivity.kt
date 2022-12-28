package myapplication.second.workinghourmanagement

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import myapplication.second.workinghourmanagement.databinding.OwnerJoinCompleteActivityBinding

class OwnerJoinCompleteActivity: AppCompatActivity() {
    private lateinit var binding: OwnerJoinCompleteActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.owner_join_complete_activity)

        binding.btnOk.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }
}