package myapplication.second.workinghourmanagement.store

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.RetrofitService
import myapplication.second.workinghourmanagement.databinding.ActivityOwnerPostScheduleBinding

class OwnerPostScheduleActivity: AppCompatActivity() {

    private lateinit var binding: ActivityOwnerPostScheduleBinding
    private lateinit var service: RetrofitService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_owner_post_schedule)
        binding.lifecycleOwner = this

        setupView()
        setupListeners()
    }

    private fun setupView() {

    }

    private fun setupListeners() {
        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.cvScheduleItemColor.setOnClickListener {
            intentSelectColorPalette()
        }

        binding.containerDate.setOnClickListener {
            openSetDateBottomSheetDialogFragment()
        }

        binding.containerTime.setOnClickListener {
            openSetTimeBottomSheetDialogFragment()
        }

        binding.btnPostSchedule.setOnClickListener {
            postSchedule()
        }
    }

    private fun postSchedule() {
        // TODO: 일정 등록할 때 필요한 API 구현
        val scheduleInfo = HashMap<String, String>()
    }

    private fun openSetDateBottomSheetDialogFragment() {
        val bottomSheet = BottomSheetSetDate()
        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
    }

    private fun openSetTimeBottomSheetDialogFragment() {
        val bottomSheet = BottomSheetSetTime()
        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
    }

    private fun intentSelectColorPalette() {
        val intent = SelectColorPaletteActivity.getIntent(this)
        startActivity(intent)
    }

    companion object {
        fun getIntent(context: Context) =
            Intent(context, OwnerPostScheduleActivity::class.java)
    }
}