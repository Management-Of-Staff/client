package myapplication.second.workinghourmanagement.store.todo_list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.RetrofitManager
import myapplication.second.workinghourmanagement.RetrofitService
import myapplication.second.workinghourmanagement.databinding.ActivityOwnerStaffTodoListManagementBinding
import myapplication.second.workinghourmanagement.dto.todo_list.ResponseGetStaffTodo
import myapplication.second.workinghourmanagement.dto.todo_list.ResponseGetStaffTodoList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OwnerStaffTodoListManagementActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOwnerStaffTodoListManagementBinding
    private lateinit var service: RetrofitService
    private lateinit var ownerStaffTodoListManagementAdapter: OwnerStaffTodoListManagementAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_owner_staff_todo_list_management)

        service = RetrofitManager.retrofit.create(RetrofitService::class.java)

        binding.lifecycleOwner = this

        setupView()
        setupListeners()
    }

    private fun setupView() {
        val storeId = 0

        service.getStaffTodoList(storeId)
            .enqueue(object : Callback<ResponseGetStaffTodoList> {
                override fun onResponse(
                    call: Call<ResponseGetStaffTodoList>,
                    response: Response<ResponseGetStaffTodoList>
                ) {
                    val body = response.body()
                    if (response.isSuccessful) {
                        body?.let {
                            initRecyclerView(body.data, binding.rvStaffTodoList)
                        }
                    } else if (response.code() == 401) {
                        Toast.makeText(
                            this@OwnerStaffTodoListManagementActivity,
                            "Unauthorized",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (response.code() == 403) {
                        Toast.makeText(
                            this@OwnerStaffTodoListManagementActivity,
                            "Forbidden",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (response.code() == 404) {
                        Toast.makeText(
                            this@OwnerStaffTodoListManagementActivity,
                            "Not Found",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        return
                    }
                }

                override fun onFailure(call: Call<ResponseGetStaffTodoList>, t: Throwable) {
                    Log.e("fail", "get staff todo-list failed... Why? : " + t.message.orEmpty())
                }
            })

    }

    private fun initRecyclerView(staffTodoList: List<ResponseGetStaffTodo>, recyclerView: RecyclerView) {
        ownerStaffTodoListManagementAdapter = OwnerStaffTodoListManagementAdapter(
            onClick = ::showTodoDetail
        )

        recyclerView.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = ownerStaffTodoListManagementAdapter
        }

        ownerStaffTodoListManagementAdapter.submitList(staffTodoList)
    }

    private fun setupListeners() {
        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.fabAddStaffTodo.setOnClickListener {
            intentOwnerPostStaffTodo()
        }
    }

    private fun intentOwnerPostStaffTodo() {
        val intent = OwnerPostStaffTodoActivity.getIntent(this)
        startActivity(intent)
    }

    private fun showTodoDetail(responseGetStaffTodo: ResponseGetStaffTodo) {
        val intent = OwnerStaffTodoDetailActivity.getIntent(this)
        startActivity(intent)
    }

    companion object {
        fun getIntent(context: Context) =
            Intent(context, OwnerStaffTodoListManagementActivity::class.java)
    }
}