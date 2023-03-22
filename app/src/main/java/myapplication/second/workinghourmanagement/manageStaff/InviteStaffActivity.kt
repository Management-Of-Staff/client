package myapplication.second.workinghourmanagement.manageStaff

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.RetrofitManager
import myapplication.second.workinghourmanagement.databinding.ActivityInviteStaffBinding
import myapplication.second.workinghourmanagement.dto.ResultResponse
import myapplication.second.workinghourmanagement.dto.manageStaff.ResponseFindStaff
import myapplication.second.workinghourmanagement.retrofit.ManageStaffService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

class InviteStaffActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInviteStaffBinding
    private lateinit var service: ManageStaffService
    private var staffId by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_invite_staff)
        service = RetrofitManager.retrofit.create(ManageStaffService::class.java)

        bind()
    }

    private fun bind() {
        binding.inviteToolbar.ivBack.setOnClickListener { finish() }
        binding.searchIcon.setOnClickListener { findStaff() }
        binding.btnInviteStaff.setOnClickListener {
            //fixme: 매장 id 등록
            service.inviteStaff(staffId, 2).enqueue(object : Callback<ResultResponse> {
                override fun onResponse(
                    call: Call<ResultResponse>,
                    response: Response<ResultResponse>
                ) {
                    finish()
                }

                override fun onFailure(call: Call<ResultResponse>, t: Throwable) {
                    Log.e("inviteStaff fail", t.message.orEmpty())
                }
            })
        }
    }

    private fun findStaff() {
        val findStaffToInviteRequest = HashMap<String, String>()
        findStaffToInviteRequest["phoneNum"] = binding.searchView.text.toString()
        service.staffSearch(findStaffToInviteRequest)
            .enqueue(object : Callback<ResponseFindStaff> {
                override fun onResponse(
                    call: Call<ResponseFindStaff>,
                    response: Response<ResponseFindStaff>
                ) {
                    val body = response.body()
                    if (body != null) {
                        if (body.statusCode == 200) {
                            staffId = body.data.staffId
                            binding.tvStaffName.text = body.data.memberName
                            binding.ivStaffProfile.visibility = View.VISIBLE
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseFindStaff>, t: Throwable) {
                    Log.e("findStaff fail", t.message.orEmpty())
                }
            })
    }
}