package myapplication.second.workinghourmanagement.manageStaff

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.RetrofitManager
import myapplication.second.workinghourmanagement.RetrofitService
import myapplication.second.workinghourmanagement.databinding.ActivityStaffListBinding
import myapplication.second.workinghourmanagement.dto.manageStaff.Staff
import myapplication.second.workinghourmanagement.dto.manageStaff.StaffList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StaffListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStaffListBinding
    private lateinit var service: RetrofitService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_staff_list)
        binding.lifecycleOwner = this
        service = RetrofitManager.retrofit.create(RetrofitService::class.java)

        setUpView()

        bind()
    }

    private fun setUpView() {
        initStaffList()
    }

    private fun initStaffList() {
        //fixme storeId 로컬 db에서 가져오기
        val storeId = 11
        service.getStaffList(storeId).enqueue(object : Callback<StaffList> {
            override fun onResponse(call: Call<StaffList>, response: Response<StaffList>) {
                val body = response.body()
                if (body != null) {
                    if (body.statusCode == 200) {
                        initStaffRecyclerView(body.data, binding.rvStaff)
                    }
                }
            }

            override fun onFailure(call: Call<StaffList>, t: Throwable) {
                Log.e("getStaffList fail", t.message.orEmpty())
            }
        })
    }

    private fun initStaffRecyclerView(staffList: List<Staff>, recyclerView: RecyclerView) {
        val staffListAdapter = StaffListAdapter()
        recyclerView.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = staffListAdapter
        }
        staffListAdapter.submitList(staffList)
    }

    private fun bind() {
        binding.toolbar.btnRightText.setOnClickListener {
            val intent = Intent(this, InviteStaffActivity::class.java)
            startActivity(intent)
        }

        binding.btnTest.setOnClickListener {
            val intent = Intent(this, ManageStaffActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        fun getIntent(context: Context) =
            Intent(context, StaffListActivity::class.java)
    }
}
