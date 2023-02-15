package myapplication.second.workinghourmanagement.manageStaff

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.databinding.ActivityStaffListBinding

class StaffListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStaffListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_staff_list)

        bind()
    }

    private fun bind() {
        binding.btnInvite.setOnClickListener {
            val intent = Intent(this, InviteStaffActivity::class.java)
            startActivity(intent)
        }

        binding.btnTest.setOnClickListener {
            val intent = Intent(this, ManageStaffActivity::class.java)
            startActivity(intent)
        }
    }
    //todo 직원리스트조회

    companion object {
        fun getIntent(context: Context) =
            Intent(context, StaffListActivity::class.java)
    }
}
