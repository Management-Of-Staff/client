package myapplication.second.workinghourmanagement.store.todo_list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import myapplication.second.workinghourmanagement.MyApplication
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.RetrofitManager
import myapplication.second.workinghourmanagement.RetrofitService
import myapplication.second.workinghourmanagement.databinding.ActivityOwnerPostStaffTodoBinding
import myapplication.second.workinghourmanagement.dto.todo_list.ResponsePostStaffTodo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        val storeId = MyApplication.prefs.getString("storeId")

        val managerCreateDtoList = HashMap<String, String>()

//        managerCreateDtoList["managerId"]
//        managerCreateDtoList["managerName"]
//        managerCreateDtoList["staffId"]


        val todoListDetailCreateDtoList = HashMap<String, String>()

//        todoListDetailCreateDtoList["completeCheck"]
//        todoListDetailCreateDtoList["completeTime"]
//        todoListDetailCreateDtoList["todoListDetail"]
//        todoListDetailCreateDtoList["todoListDetailId"]


        val todoListCreateDto = HashMap<String, String>()

//        todoListCreateDto["managerCreateDtoList"]
//        todoListCreateDto["taskStartTime"]
//        todoListCreateDto["todoListDetailCreateDtoList"]
//        todoListCreateDto["todoListTitle"]

        service.postStaffTodo(storeId, todoListCreateDto)
            .enqueue(object : Callback<ResponsePostStaffTodo> {
                override fun onResponse(
                    call: Call<ResponsePostStaffTodo>,
                    response: Response<ResponsePostStaffTodo>
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()

                        body?.let {
                            onPostStaffTodoSuccess()
                        }
                    } else if (response.code() == 401) {
                        Toast.makeText(
                            this@OwnerPostStaffTodoActivity,
                            "Unauthorized",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (response.code() == 403) {
                        Toast.makeText(
                            this@OwnerPostStaffTodoActivity,
                            "Forbidden",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (response.code() == 404) {
                        Toast.makeText(
                            this@OwnerPostStaffTodoActivity,
                            "Not Found",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        return
                    }
                }

                override fun onFailure(call: Call<ResponsePostStaffTodo>, t: Throwable) {
                    Log.e("fail", "post staff todo failed... Why? : " + t.message.orEmpty())
                }

            })
    }

    private fun onPostStaffTodoSuccess() {
        val bottomSheetDialogFragment = BottomSheetOwnerCompletedPostStaffTodo()
        bottomSheetDialogFragment.show(supportFragmentManager, bottomSheetDialogFragment.tag)
    }

    private fun openSetDateBottomSheet() {
        val bottomSheet = BottomSheetSetDate()
        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
    }

    private fun openSetTimeBottomSheet() {
        val bottomSheet = BottomSheetOwnerSetStaffTodoStartTime()
        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
    }

    private fun openTodoManagerChoiceBottomSheet() {
        val bottomsheet = BottomSheetOwnerSelectTodoManager()
        bottomsheet.show(supportFragmentManager, bottomsheet.tag)
    }

    companion object {
        fun getIntent(context: Context) =
            Intent(context, OwnerPostStaffTodoActivity::class.java)
    }
}