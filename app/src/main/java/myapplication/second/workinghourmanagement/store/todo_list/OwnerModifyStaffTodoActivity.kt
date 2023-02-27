package myapplication.second.workinghourmanagement.store.todo_list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import myapplication.second.workinghourmanagement.MyApplication
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.RetrofitManager
import myapplication.second.workinghourmanagement.RetrofitService
import myapplication.second.workinghourmanagement.databinding.ActivityOwnerModifyStaffTodoBinding
import myapplication.second.workinghourmanagement.dto.todo_list.ResponseModifyStaffTodo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        val storeId = MyApplication.prefs.getString("storeId").length

        val todoListId = MyApplication.prefs.getString("todoListId").length

        val managerCreateDtoList = HashMap<String, String>()

//        managerCreateDtoList["managerId"]
//        managerCreateDtoList["managerName"]
//        managerCreateDtoList["staffId"]


        val todoListDetailCreateDtoList = HashMap<String, String>()

//        todoListDetailCreateDtoList["completeCheck"]
//        todoListDetailCreateDtoList["completeTime"]
//        todoListDetailCreateDtoList["todoListDetail"]
//        todoListDetailCreateDtoList["todoListDetailId"]

        val todoListUpdateDto = HashMap<String, String>()

        todoListUpdateDto["managerCreateDtoList"]
        todoListUpdateDto["taskStartTime"]
        todoListUpdateDto["todoListDetailCreateDtoList"]
        todoListUpdateDto["todoListTitle"]

        service.modifyStaffTodo(storeId, todoListId, todoListUpdateDto)
            .enqueue(object : Callback<ResponseModifyStaffTodo> {
                override fun onResponse(
                    call: Call<ResponseModifyStaffTodo>,
                    response: Response<ResponseModifyStaffTodo>
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()

                        body?.let {
                            onModifyStaffTodoSuccess()
                        }
                    } else if (response.code() == 401) {
                        Toast.makeText(
                            this@OwnerModifyStaffTodoActivity,
                            "Unauthorized",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (response.code() == 403) {
                        Toast.makeText(
                            this@OwnerModifyStaffTodoActivity,
                            "Forbidden",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (response.code() == 404) {
                        Toast.makeText(
                            this@OwnerModifyStaffTodoActivity,
                            "Not Found",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        return
                    }
                }

                override fun onFailure(call: Call<ResponseModifyStaffTodo>, t: Throwable) {
                    Log.e("fail", "modify staff todo failed... Why? : " + t.message.orEmpty())
                }

            })
    }

    private fun onModifyStaffTodoSuccess() {
        val bottomSheetDialogFragment = BottomSheetOwnerCompletedModifyStaffTodo()
        bottomSheetDialogFragment.show(supportFragmentManager, bottomSheetDialogFragment.tag)
    }

    companion object {
        fun getIntent(context: Context) =
            Intent(context, OwnerModifyStaffTodoActivity::class.java)
    }
}