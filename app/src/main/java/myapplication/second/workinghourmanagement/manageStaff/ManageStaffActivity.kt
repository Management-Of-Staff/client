package myapplication.second.workinghourmanagement.manageStaff

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.databinding.ActivityManageStaffBinding

class ManageStaffActivity : AppCompatActivity() {
    private lateinit var binding: ActivityManageStaffBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_manage_staff)

        bind()
    }

    private fun bind() {
        binding.toolbar.ivBack.setOnClickListener { finish() }
        binding.btnAddSchedule.setOnClickListener {
            val bottomSheet = BottomSheetAddWorkSchedule(this)
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.position,
            android.R.layout.simple_spinner_dropdown_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerStaffPosition.adapter = adapter

        binding.btnViewStaffProfile.setOnClickListener {
            val intent = Intent(this, StaffPersonalInfoActivity::class.java)
            startActivity(intent)
        }
    }
}