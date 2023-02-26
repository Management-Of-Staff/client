package myapplication.second.workinghourmanagement.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import myapplication.second.workinghourmanagement.MyApplication
import myapplication.second.workinghourmanagement.RetrofitManager
import myapplication.second.workinghourmanagement.RetrofitService
import myapplication.second.workinghourmanagement.databinding.FragmentOwnerProfileBinding
import myapplication.second.workinghourmanagement.member.LoginActivity
import myapplication.second.workinghourmanagement.profile.OwnerProfileInfoActivity
import myapplication.second.workinghourmanagement.profile.SettingNotificationActivity
import myapplication.second.workinghourmanagement.profile.ShowTosActivity
import myapplication.second.workinghourmanagement.vm.UserInfoViewModel

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentOwnerProfileBinding
    private lateinit var service: RetrofitService
    private lateinit var viewModel: UserInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentOwnerProfileBinding.inflate(layoutInflater)
        service = RetrofitManager.retrofit.create(RetrofitService::class.java)
        viewModel = ViewModelProvider(this)[UserInfoViewModel::class.java]
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.btnViewProfile.setOnClickListener {
            activity?.let {
                val intent = Intent(context, OwnerProfileInfoActivity::class.java)
                startActivity(intent)
            }
        }

        binding.btnLogout.setOnClickListener {
            activity?.let {
                val intent = Intent(context, LoginActivity::class.java)
                MyApplication.prefs.setString("accessToken", "")
                MyApplication.prefs.setString("refreshToken", "")
                startActivity(intent)
            }
        }

        binding.btnSettingNotification.setOnClickListener {
            val intent = Intent(context, SettingNotificationActivity::class.java)
            startActivity(intent)
        }

        binding.btnShowTos.setOnClickListener {
            val intent = Intent(context, ShowTosActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    companion object {
        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }
}