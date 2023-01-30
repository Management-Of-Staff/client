package myapplication.second.workinghourmanagement.store

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.RetrofitService
import myapplication.second.workinghourmanagement.databinding.ActivityOwnerModifyScheduleBinding

class OwnerModifyScheduleActivity: AppCompatActivity() {

    private lateinit var binding: ActivityOwnerModifyScheduleBinding
    private lateinit var service: RetrofitService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_owner_modify_schedule)
        binding.lifecycleOwner = this

        setupView()
        setupListeners()
    }

    private fun setupView() {
        TODO("Not yet implemented")
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

        binding.btnModifySchedule.setOnClickListener {
            // TODO: 스케줄 변경할 때의 API 연동 구현
        }
    }

    private fun openSetDateBottomSheetDialogFragment() {
        val intent = BottomSheetSetDate.getIntent(this)
        startActivity(intent)
    }

    private fun openSetTimeBottomSheetDialogFragment() {
        val intent = BottomSheetSetTime.getIntent(this)
        startActivity(intent)
    }

    private fun intentSelectColorPalette() {
        val intent = SelectColorPaletteActivity.getIntent(this)
        startActivity(intent)
    }

    companion object {
        fun getIntent(context: Context) =
            Intent(context, OwnerModifyScheduleActivity::class.java)
    }
}