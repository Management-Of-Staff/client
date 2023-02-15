package myapplication.second.workinghourmanagement.manageStaff

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.databinding.ActivityOwnerInviteStaffBinding

class InviteStaffActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOwnerInviteStaffBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_owner_invite_staff)
    }
}