package myapplication.second.workinghourmanagement.manageStaff

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.databinding.ActivityStaffPersonalInfoBinding

class StaffPersonalInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStaffPersonalInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_staff_personal_info)

        binding.toolbar.ivBack.setOnClickListener { finish() }
    }
}