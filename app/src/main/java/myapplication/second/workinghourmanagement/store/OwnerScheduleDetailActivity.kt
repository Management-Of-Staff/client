package myapplication.second.workinghourmanagement.store

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import myapplication.second.workinghourmanagement.databinding.ActivityOwnerScheduleDetailBinding
import myapplication.second.workinghourmanagement.store.AskDeleteScheduleDialogFragment.Companion.TAG


class OwnerScheduleDetailActivity: AppCompatActivity() {
    private lateinit var binding: ActivityOwnerScheduleDetailBinding

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
    }
}