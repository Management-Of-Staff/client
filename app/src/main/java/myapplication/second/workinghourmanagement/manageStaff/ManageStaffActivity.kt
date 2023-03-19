package myapplication.second.workinghourmanagement.manageStaff

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.RetrofitManager
import myapplication.second.workinghourmanagement.RetrofitService
import myapplication.second.workinghourmanagement.databinding.ActivityManageStaffBinding
import myapplication.second.workinghourmanagement.dto.manageStaff.ResponseGetStaffInfo
import myapplication.second.workinghourmanagement.dto.manageStaff.Staff
import myapplication.second.workinghourmanagement.dto.manageStaff.StaffInfo
import myapplication.second.workinghourmanagement.dto.manageStaff.WorkTimeRequest
import myapplication.second.workinghourmanagement.store.OwnerStoreConversionAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ManageStaffActivity : AppCompatActivity() {
    private lateinit var binding: ActivityManageStaffBinding
    private lateinit var service: RetrofitService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_manage_staff)
        service = RetrofitManager.retrofit.create(RetrofitService::class.java)

        val staffId = intent.getIntExtra("staffId", 0)

        initStaffInfo(staffId)

        bind()
    }

    private fun initStaffInfo(staffId: Int) {
        //fixme : storeId 수정
        service.getStaffInfo(staffId, 1).enqueue(object : Callback<ResponseGetStaffInfo> {
            override fun onResponse(
                call: Call<ResponseGetStaffInfo>,
                response: Response<ResponseGetStaffInfo>
            ) {
                val body = response.body()
                if (body != null) {
                    if (body.statusCode == 200) {
                        Log.d("getStaffInfo ttttt", body.data.toString())
                        binding.staffName.text = body.data.name
                        binding.staffPhone.text = body.data.phone

                        if(body.data.readWorkTimesWithStaffList.isNotEmpty()){
                            binding.comment.visibility = View.GONE
                            binding.rvWorkSchedule.visibility = View.VISIBLE
                            initWorkSchedule(body.data.readWorkTimesWithStaffList, binding.rvWorkSchedule)
                        }
                    }
                    else{
                        Log.d("tttt", response.raw().toString())
                    }

                }
            }

            override fun onFailure(call: Call<ResponseGetStaffInfo>, t: Throwable) {
                Log.e("getStaffInfo fail", t.message.orEmpty())
            }
        })
    }

    private fun initWorkSchedule(workList: List<WorkTimeRequest>, recyclerView: RecyclerView) {
        val workScheduleAdapter = WorkScheduleAdapter()

        recyclerView.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = workScheduleAdapter
        }
        workScheduleAdapter.submitList(workList)
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