package myapplication.second.workinghourmanagement.manageStaff

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.RetrofitManager
import myapplication.second.workinghourmanagement.databinding.ActivityManageStaffBinding
import myapplication.second.workinghourmanagement.dto.ResultResponse
import myapplication.second.workinghourmanagement.dto.manageStaff.ResponseGetStaffInfo
import myapplication.second.workinghourmanagement.dto.manageStaff.UpdateRankAndWageRequest
import myapplication.second.workinghourmanagement.dto.manageStaff.WorkTime
import myapplication.second.workinghourmanagement.retrofit.ManageStaffService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

class ManageStaffActivity : AppCompatActivity() {
    private lateinit var binding: ActivityManageStaffBinding
    private lateinit var service: ManageStaffService
    private lateinit var role: String
    private var staffId by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_manage_staff)
        service = RetrofitManager.retrofit.create(ManageStaffService::class.java)

        val employmentId = intent.getIntExtra("employmentId", 2)

        initStaffInfo(employmentId)
        bind(employmentId)
    }

    private fun modifyStaffInfo(employmentId: Int) {
        val data = UpdateRankAndWageRequest(binding.etMoney.text.toString().toInt(), role)
        service.modifyStaffInfo(employmentId, data).enqueue(object : Callback<ResultResponse> {
            override fun onResponse(
                call: Call<ResultResponse>,
                response: Response<ResultResponse>
            ) {
                finish()
            }

            override fun onFailure(call: Call<ResultResponse>, t: Throwable) {
                Log.e("modifyStaffInfo fail", t.message.orEmpty())
            }
        })
    }

    private fun initStaffInfo(employmentId: Int) {
        //fixme : storeId 수정
        service.getStaffInfo(employmentId).enqueue(object : Callback<ResponseGetStaffInfo> {
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
                        if (body.data.hourlyWage != null) {
                            binding.etMoney.setText(body.data.hourlyWage.toString())
                        }
                        staffId = body.data.staffId
                        when (body.data.rank) {
                            "MANAGER" -> binding.spinnerStaffPosition.setSelection(0)
                            "PULL_TIME_EMPLOYEE" -> binding.spinnerStaffPosition.setSelection(1)
                            "PART_TIME_WORKER" -> binding.spinnerStaffPosition.setSelection(2)
                            "ETC" -> binding.spinnerStaffPosition.setSelection(3)
                            else -> binding.spinnerStaffPosition.setSelection(3)
                        }

                        if (body.data.readWorkTimesWithStaffList.isNotEmpty()) {
                            binding.comment.visibility = View.GONE
                            binding.rvWorkSchedule.visibility = View.VISIBLE
                            initWorkSchedule(
                                body.data.readWorkTimesWithStaffList,
                                binding.rvWorkSchedule
                            )
                        }
                    } else {
                        Log.d("tttt", response.raw().toString())
                    }

                }
            }

            override fun onFailure(call: Call<ResponseGetStaffInfo>, t: Throwable) {
                Log.e("getStaffInfo fail", t.message.orEmpty())
            }
        })
    }

    private fun initWorkSchedule(workList: List<WorkTime>, recyclerView: RecyclerView) {
        val workScheduleAdapter = WorkScheduleAdapter()

        recyclerView.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = workScheduleAdapter
        }
        workScheduleAdapter.submitList(workList)
    }

    private fun bind(employmentId: Int) {
        binding.toolbar.ivBack.setOnClickListener { finish() }
        binding.toolbar.btnRightText.setOnClickListener {
            //todo 직원 삭제(보류)
        }
        binding.btnViewStaffProfile.setOnClickListener {
            val intent = Intent(this, StaffPersonalInfoActivity::class.java)
            startActivity(intent)
        }

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.position,
            android.R.layout.simple_spinner_dropdown_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerStaffPosition.adapter = adapter
        binding.spinnerStaffPosition.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        0 -> role = "MANAGER"
                        1 -> role = "PULL_TIME_EMPLOYEE"
                        2 -> role = "PART_TIME_WORKER"
                        3 -> role = "ETC"
                    }
                }
            }

        binding.btnDeleteSchedule.setOnClickListener {
            //todo 근무 일정 삭제
        }

        binding.btnAddSchedule.setOnClickListener {
            val bottomSheet = BottomSheetAddWorkSchedule(this, employmentId, staffId)
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }
        binding.btnSave.setOnClickListener {
            modifyStaffInfo(employmentId)
        }
    }
}