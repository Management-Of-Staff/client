package myapplication.second.workinghourmanagement.store

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.RetrofitManager
import myapplication.second.workinghourmanagement.RetrofitService
import myapplication.second.workinghourmanagement.databinding.ActivityOwnerStaffTodoDetailBinding

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

    }

    private fun initRecyclerView(recyclerView: RecyclerView) {

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
        TODO("Not yet implemented")
    }

    companion object {
        fun getIntent(context: Context) =
            Intent(context, OwnerStaffTodoDetailActivity::class.java)
    }
}