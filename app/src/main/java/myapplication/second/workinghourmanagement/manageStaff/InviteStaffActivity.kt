package myapplication.second.workinghourmanagement.manageStaff

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.databinding.ActivityInviteStaffBinding

class InviteStaffActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInviteStaffBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_invite_staff)
    }
}