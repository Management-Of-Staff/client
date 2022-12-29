package myapplication.second.workinghourmanagement

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import myapplication.second.workinghourmanagement.databinding.ActivityOwnerSearchBaseAddressBinding

class OwnerSearchBaseAddressActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityOwnerSearchBaseAddressBinding
    private lateinit var ownerSearchBaseAddressAdapter: OwnerSearchBaseAddressAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_owner_search_base_address)

        binding.lifecycleOwner = this

        setupView()
        setupListeners()
    }

    private fun setupView() {
        initRecyclerView(binding.rvBaseAddressList)
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        ownerSearchBaseAddressAdapter = OwnerSearchBaseAddressAdapter()

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

        binding.ivSearchBaseAddress.setOnClickListener {

        }
    }

    companion object {
        fun getIntent(context: Context) =
            Intent(context, OwnerSearchBaseAddressActivity::class.java)
    }

    override fun onClick(view: View) {

    }
}