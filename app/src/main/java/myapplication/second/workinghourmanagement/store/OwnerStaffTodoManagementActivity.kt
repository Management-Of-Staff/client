package myapplication.second.workinghourmanagement.store

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.RetrofitManager
import myapplication.second.workinghourmanagement.RetrofitService
import myapplication.second.workinghourmanagement.databinding.ActivityOwnerStaffTodoManagementBinding
import myapplication.second.workinghourmanagement.dto.ResultGetOwnerStaffTodo

class OwnerStaffTodoManagementActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOwnerStaffTodoManagementBinding
    private lateinit var service: RetrofitService
    private lateinit var ownerStaffTodoListManagementAdapter: OwnerStaffTodoListManagementAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_owner_staff_todo_management)

        service = RetrofitManager.retrofit.create(RetrofitService::class.java)

        binding.lifecycleOwner = this

        setupView()
        setupListeners()
    }

    private fun setupView() {
        initRecyclerView(binding.rvStaffTodoList)
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        ownerStaffTodoListManagementAdapter = OwnerStaffTodoListManagementAdapter(
            onClick = ::showTodoDetail
        )

        recyclerView.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = ownerStaffTodoListManagementAdapter
        }
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

    private fun showTodoDetail(resultGetOwnerStaffTodo: ResultGetOwnerStaffTodo) {
        val intent = OwnerStaffTodoDetailActivity.getIntent(this)
        startActivity(intent)
    }

    companion object {
        fun getIntent(context: Context) =
            Intent(context, OwnerStaffTodoManagementActivity::class.java)
    }
}