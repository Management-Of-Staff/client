package myapplication.second.workinghourmanagement

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import myapplication.second.workinghourmanagement.databinding.ActivityOwnerConvertStoreBinding

class OwnerConvertStoreActivity: AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityOwnerConvertStoreBinding
    private lateinit var ownerConvertStoreAdapter: OwnerConvertStoreAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_owner_convert_store)

        binding.lifecycleOwner = this

        setupView()
        setupListener()
    }

    private fun setupView() {
        initRecyclerView(binding.rvStoreList)
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        ownerConvertStoreAdapter = OwnerConvertStoreAdapter()

        recyclerView.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
//            adapter = ownerConvertStoreAdapter
        }
    }

    private fun setupListener() {
        binding.ivOptionMenu.setOnClickListener {

        }

        binding.buttonRegisterStore.setOnClickListener {

        }

        binding.buttonDeleteStore.setOnClickListener {

        }
    }

    override fun onClick(view: View) {
    }
}