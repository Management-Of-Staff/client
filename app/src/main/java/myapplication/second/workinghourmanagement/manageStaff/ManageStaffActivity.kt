package myapplication.second.workinghourmanagement.manageStaff

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.databinding.ActivityManageStaffBinding

class ManageStaffActivity: AppCompatActivity() {
    private lateinit var binding: ActivityManageStaffBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_manage_staff)
    }
}