package myapplication.second.workinghourmanagement.manageStaff

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.RetrofitManager
import myapplication.second.workinghourmanagement.RetrofitService
import myapplication.second.workinghourmanagement.databinding.ActivityInviteStaffBinding
import myapplication.second.workinghourmanagement.dto.manageStaff.ResponseFindStaff
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InviteStaffActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInviteStaffBinding
    private lateinit var service: RetrofitService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_invite_staff)
        service = RetrofitManager.retrofit.create(RetrofitService::class.java)

        bind()
    }

    private fun bind() {
        binding.inviteToolbar.ivBack.setOnClickListener { finish() }
        binding.searchIcon.setOnClickListener {
            findStaff()
        }
        binding.btnInviteStaff.setOnClickListener {
            // 직원 초대

        }
    }

    private fun findStaff() {
        val findStaffToInviteRequest = HashMap<String, String>()
        findStaffToInviteRequest["phone"] = binding.searchView.text.toString()
        service.findStaff(findStaffToInviteRequest)
            .enqueue(object : Callback<ResponseFindStaff> {
                override fun onResponse(
                    call: Call<ResponseFindStaff>,
                    response: Response<ResponseFindStaff>
                ) {
                    val body = response.body()
                    if (body != null) {
                        if (body.statusCode == 200) {
                            binding.tvStaffName.text = body.data.name
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseFindStaff>, t: Throwable) {
                    Log.e("findStaff fail", t.message.orEmpty())
                }
            })
    }
}