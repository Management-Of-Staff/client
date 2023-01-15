package myapplication.second.workinghourmanagement.store

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.RetrofitManager
import myapplication.second.workinghourmanagement.RetrofitService
import myapplication.second.workinghourmanagement.databinding.ActivityOwnerPostStaffTodoBinding

class OwnerPostStaffTodoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOwnerPostStaffTodoBinding
    private lateinit var service: RetrofitService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_owner_post_staff_todo)
        binding.lifecycleOwner = this

        service = RetrofitManager.retrofit.create(RetrofitService::class.java)

        setupListeners()
    }

    private fun setupView() {

    }

    private fun setupListeners() {
        binding.ivBack.setOnClickListener {
            finish()
        }

//        binding.containerDate.setOnClickListener {
//            openSetDateBottomSheetDialogFragment()
//        }

        binding.cvTodoStartTime.setOnClickListener {
            openSetTimeBottomSheet()
        }

        binding.ivAddTodoManager.setOnClickListener {
            openTodoManagerChoiceBottomSheet()
        }

        binding.btnPostStaffTodo.setOnClickListener {
            openPostStaffTodoDialog()
        }
    }

    private fun openPostStaffTodoDialog() {
        val deleteMessage = getString(R.string.do_you_want_to_post_staff_todo)
        MaterialAlertDialogBuilder(this)
            .setMessage(deleteMessage)
            .setNegativeButton(getString(R.string.no))
            { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(getString(R.string.yes))
            { dialog, _ ->
                postStaffTodo()
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun postStaffTodo() {
        // TODO: 일정 등록할 때 필요한 API 구현
        val staffTodoInfo = HashMap<String, String>()
    }

    private fun openSetDateBottomSheet() {
        val bottomSheet = BottomSheetSetDateDialogFragment()
        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
    }

    private fun openSetTimeBottomSheet() {
        val bottomSheet = BottomSheetOwnerSetStaffTodoStartTimeDialogFragment()
        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
    }

    private fun openTodoManagerChoiceBottomSheet() {
        val bottomsheet = BottomSheetOwnerChooseTodoManagerDialogFragment()
        bottomsheet.show(supportFragmentManager, bottomsheet.tag)
    }

    companion object {
        fun getIntent(context: Context) =
            Intent(context, OwnerPostStaffTodoActivity::class.java)
    }
}