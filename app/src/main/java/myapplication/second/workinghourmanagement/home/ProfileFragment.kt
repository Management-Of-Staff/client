package myapplication.second.workinghourmanagement.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import myapplication.second.workinghourmanagement.MyApplication
import myapplication.second.workinghourmanagement.RetrofitManager
import myapplication.second.workinghourmanagement.RetrofitService
import myapplication.second.workinghourmanagement.databinding.FragmentOwnerProfileBinding
import myapplication.second.workinghourmanagement.dto.ResultUserInfo
import myapplication.second.workinghourmanagement.profile.OwnerProfileInfoActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentOwnerProfileBinding
    private lateinit var service: RetrofitService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOwnerProfileBinding.inflate(layoutInflater)
        service = RetrofitManager.retrofit.create(RetrofitService::class.java)

        binding.btnViewProfile.setOnClickListener {
            activity?.let {
                getUserInfo()
            }
        }
        return binding.root
    }

    companion object {
        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }

    // 회원정보 조회
    private fun getUserInfo() {
        val tok = "Bearer " + MyApplication.prefs.getString("accessToken")
        service.selectOwnerInfo(
            tok
        ).enqueue(object : Callback<ResultUserInfo> {
            override fun onResponse(
                call: Call<ResultUserInfo>,
                response: Response<ResultUserInfo>
            ) {
                val body = response.body()
                if (body != null) {
                    if (body.statusCode == 200) {
                        val data = body.data
                        Log.d(" tag[200],", data.toString())
                        val intent = Intent(context, OwnerProfileInfoActivity::class.java)
                        intent.putExtra("userInfo", data)
                        startActivity(intent)
                    } else {
                        Log.d(" tag[400],", body.message)
                    }
                }
            }

            override fun onFailure(call: Call<ResultUserInfo>, t: Throwable) {
                Log.e("getPosts fail", "[Fail]$t")
            }
        })
    }
}