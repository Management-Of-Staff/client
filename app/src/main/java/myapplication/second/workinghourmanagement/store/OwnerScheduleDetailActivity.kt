package myapplication.second.workinghourmanagement.store

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import myapplication.second.workinghourmanagement.RetrofitService
import myapplication.second.workinghourmanagement.databinding.ActivityOwnerScheduleDetailBinding
import myapplication.second.workinghourmanagement.store.AskDeleteScheduleDialogFragment.Companion.TAG


class OwnerScheduleDetailActivity: AppCompatActivity() {
    private lateinit var binding: ActivityOwnerScheduleDetailBinding
    private lateinit var service: RetrofitService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOwnerScheduleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnDeleteScheduleItem.setOnClickListener {
            deleteSchedule()
        }

        binding.btnModifyScheduleItem.setOnClickListener {
            modifySchedule()
        }
    }

    private fun deleteSchedule() {
        openDeleteScheduleDialogFragment()
        // TODO: 스케줄 삭제할 때의 API 연동 구현
    }

    private fun openDeleteScheduleDialogFragment() {
        val askDeleteScheduleDialogFragment = AskDeleteScheduleDialogFragment.newInstance()

        askDeleteScheduleDialogFragment.show(supportFragmentManager, TAG)
    }

    private fun modifySchedule() {
        openModifyScheduleDialogFragment()
        // TODO: 스케줄 변경할 때의 API 연동 구현
    }

    private fun openModifyScheduleDialogFragment() {
        // TODO: 스케줄 변경시 이동할 곳으로 이동
        val intent = OwnerModifyScheduleActivity.getIntent(this)
        startActivity(intent)
    }

    companion object {
        fun getIntent(context: Context) =
            Intent(context, OwnerScheduleDetailActivity::class.java)
    }
}