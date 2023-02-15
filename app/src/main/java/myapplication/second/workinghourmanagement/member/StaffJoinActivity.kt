package myapplication.second.workinghourmanagement.member

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.databinding.ActivityStaffJoinBinding

class StaffJoinActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStaffJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_staff_join)

        binding.toolbar.ivBack.setOnClickListener { finish() }
    }
}