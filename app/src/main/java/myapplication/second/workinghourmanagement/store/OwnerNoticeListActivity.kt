package myapplication.second.workinghourmanagement.store

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.NonCancellable.start
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.RetrofitManager
import myapplication.second.workinghourmanagement.RetrofitService
import myapplication.second.workinghourmanagement.databinding.ActivityOwnerNoticeListBinding
import myapplication.second.workinghourmanagement.databinding.ActivityOwnerStaffTodoDetailBinding
import myapplication.second.workinghourmanagement.dto.ResultGetNotice

class OwnerNoticeListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOwnerNoticeListBinding
    private lateinit var service: RetrofitService

    private lateinit var noticeListAdapter: OwnerNoticeListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_owner_notice_list)

        service = RetrofitManager.retrofit.create(RetrofitService::class.java)

        binding.lifecycleOwner = this

        setupView()
        setupListener()
    }

    private fun setupView() {
        initRecyclerView(binding.rvNoticeList)
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        noticeListAdapter = OwnerNoticeListAdapter(
            onClick = ::intentNoticeDetail
        )
    }

    private fun intentNoticeDetail(resultGetNotice: ResultGetNotice) {
        val intent = OwnerNoticeDetailActivity.getIntent(this)
        startActivity(intent)
    }

    private fun setupListener() {
        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.fabAddNotice.setOnClickListener {
            intentOwnerPostNotice()
        }
    }

    private fun intentOwnerPostNotice() {
        val intent = OwnerPostNoticeActivity.getIntent(this)
        startActivity(intent)
    }

    companion object {
        fun getIntent(context: Context) =
            Intent(context, OwnerNoticeListActivity::class.java)
    }
}