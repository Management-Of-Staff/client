package myapplication.second.workinghourmanagement.store.notice

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.RetrofitManager
import myapplication.second.workinghourmanagement.RetrofitService
import myapplication.second.workinghourmanagement.databinding.ActivityOwnerNoticeDetailBinding

class OwnerNoticeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOwnerNoticeDetailBinding
    private lateinit var service: RetrofitService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_owner_notice_detail)

        service = RetrofitManager.retrofit.create(RetrofitService::class.java)

        binding.lifecycleOwner = this

        setupView()
        setupListener()
    }

    private fun setupView() {

    }

    private fun setupListener() {
        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnDeleteNotice.setOnClickListener {

        }

        binding.btnModifyNotice.setOnClickListener {
            intentModifyNotice()
        }
    }

    private fun intentModifyNotice() {
        val intent = OwnerModifyNoticeActivity.getIntent(this)
        startActivity(intent)
    }


    companion object {
        fun getIntent(context: Context) =
            Intent(context, OwnerNoticeDetailActivity::class.java)
    }
}