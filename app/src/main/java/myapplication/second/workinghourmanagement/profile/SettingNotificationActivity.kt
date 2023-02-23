package myapplication.second.workinghourmanagement.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.databinding.ActivitySettingNotificationBinding

class SettingNotificationActivity: AppCompatActivity() {
    private lateinit var binding: ActivitySettingNotificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting_notification)

        bind()
    }

    private fun bind() {
        binding.toolbar.ivBack.setOnClickListener { finish() }
    }
}