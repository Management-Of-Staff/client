package myapplication.second.workinghourmanagement

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import myapplication.second.workinghourmanagement.databinding.ActivityOwnerSearchPrimaryAddressBinding

class OwnerSearchPrimaryAddressActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityOwnerSearchPrimaryAddressBinding
    private lateinit var ownerSearchPrimaryAddressAdapter: OwnerSearchPrimaryAddressAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_owner_search_primary_address)

        binding.lifecycleOwner = this

        setupView()
        setupListeners()
    }

    private fun setupView() {
        initRecyclerView(binding.rvPrimaryAddressList)
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        ownerSearchPrimaryAddressAdapter = OwnerSearchPrimaryAddressAdapter()

        recyclerView.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
//            adapter = ownerConvertStoreAdapter
        }
    }

    private fun setupListeners() {
        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.ivSearchPrimaryAddress.setOnClickListener {
            // TODO: 기본주소 검색 기능 구현
        }
    }

    companion object {
        fun getIntent(context: Context) =
            Intent(context, OwnerSearchPrimaryAddressActivity::class.java)
    }

    override fun onClick(view: View) {

    }
}