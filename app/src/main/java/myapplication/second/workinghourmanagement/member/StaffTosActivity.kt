package myapplication.second.workinghourmanagement.member

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.databinding.ActivityStaffTosBinding

class StaffTosActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStaffTosBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_staff_tos)

        bind()
    }

    private fun bind() {
        binding.buttonNext.setOnClickListener {
            val intent = Intent(this, PhoneAuthActivity::class.java)
            startActivity(intent)
        }
        binding.toolbar.ivBack.setOnClickListener { finish() }
    }
}