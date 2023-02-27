package myapplication.second.workinghourmanagement.store.todo_list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.RetrofitManager
import myapplication.second.workinghourmanagement.RetrofitService
import myapplication.second.workinghourmanagement.databinding.ActivityOwnerStaffTodoDetailBinding
import myapplication.second.workinghourmanagement.dto.todo_list.ResponseGetStaffTodo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OwnerStaffTodoDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOwnerStaffTodoDetailBinding
    private lateinit var service: RetrofitService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_owner_staff_todo_detail)
        service = RetrofitManager.retrofit.create(RetrofitService::class.java)

        binding.lifecycleOwner = this

        setupView()
        setupListener()
    }

    private fun setupView() {
        val todoListId = 0

        service.getStaffTodo(todoListId)
            .enqueue(object : Callback<ResponseGetStaffTodo> {
                override fun onResponse(
                    call: Call<ResponseGetStaffTodo>,
                    response: Response<ResponseGetStaffTodo>
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()

                        body?.let {
//                            initRecyclerView(body.data, binding.rvStaffTodoList)
                        }
                    } else if (response.code() == 401) {
                        Toast.makeText(
                            this@OwnerStaffTodoDetailActivity,
                            "Unauthorized",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (response.code() == 403) {
                        Toast.makeText(
                            this@OwnerStaffTodoDetailActivity,
                            "Forbidden",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (response.code() == 404) {
                        Toast.makeText(
                            this@OwnerStaffTodoDetailActivity,
                            "Not Found",
                            Toast.LENGTH_SHORT
                        ).show()
                    }  else {
                        return
                    }
                }

                override fun onFailure(call: Call<ResponseGetStaffTodo>, t: Throwable) {
                    Log.e("fail", "get staff todo failed... Why? : " + t.message.orEmpty())
                }

            })
    }

    private fun initRecyclerView(staffTodoList: List<ResponseGetStaffTodo>,  recyclerView: RecyclerView) {

    }

    private fun setupListener() {
        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnDeleteStaffTodo.setOnClickListener {
            openDeleteStaffTodoDialog()
        }

        binding.btnModifyStaffTodo.setOnClickListener {
            intentModifyStaffTodo()
        }
    }

    private fun openDeleteStaffTodoDialog() {
        val message = getString(R.string.do_you_want_to_post_staff_todo)
        MaterialAlertDialogBuilder(this)
            .setMessage(message)
            .setNegativeButton(getString(R.string.no))
            { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(getString(R.string.yes))
            { dialog, _ ->
                deleteStaffTodo()
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun deleteStaffTodo() {
        TODO("Not yet implemented")
    }

    private fun intentModifyStaffTodo() {
        val intent = OwnerModifyStaffTodoActivity.getIntent(this)
        startActivity(intent)
    }

    companion object {
        fun getIntent(context: Context) =
            Intent(context, OwnerStaffTodoDetailActivity::class.java)
    }
}