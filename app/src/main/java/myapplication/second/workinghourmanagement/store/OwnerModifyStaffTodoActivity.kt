package myapplication.second.workinghourmanagement.store

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.RetrofitManager
import myapplication.second.workinghourmanagement.RetrofitService
import myapplication.second.workinghourmanagement.databinding.ActivityOwnerModifyStaffTodoBinding

class OwnerModifyStaffTodoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOwnerModifyStaffTodoBinding
    private lateinit var service: RetrofitService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_owner_modify_staff_todo)
        binding.lifecycleOwner = this

        service = RetrofitManager.retrofit.create(RetrofitService::class.java)

        setupView()
        setupListeners()
    }

    private fun setupView() {
        initRecyclerView(binding.rvTodoManagerList)
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        recyclerView.run {
            setHasFixedSize(true)

        }
    }

    private fun setupListeners() {
        binding.ivBack.setOnClickListener {
            finish()
        }

//        binding.containerDate.setOnClickListener {
//            openSetDateBottomSheetDialogFragment()
//        }

        binding.cvTodoStartTime.setOnClickListener {
            openSetTimeBottomSheetDialogFragment()
        }

        binding.btnModifyStaffTodo.setOnClickListener {
            modifyStaffTodo()
        }
    }

    private fun openSetDateBottomSheetDialogFragment() {
        val bottomSheet = BottomSheetSetDate()
        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
    }

    private fun openSetTimeBottomSheetDialogFragment() {
        val bottomSheet = BottomSheetOwnerSetStaffTodoStartTime()
        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
    }

    private fun modifyStaffTodo() {
        // TODO: 일정 등록할 때 필요한 API 구현
        val staffTodoInfo = HashMap<String, String>()
    }
}