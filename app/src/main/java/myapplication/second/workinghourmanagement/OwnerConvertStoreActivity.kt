package myapplication.second.workinghourmanagement

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
            binding.tvTitleConvertStore.text = "매장 삭제"

            binding.buttonRegisterStore.visibility = View.GONE
            binding.buttonDeleteStore.visibility = View.GONE
            binding.buttonChoiceDeleteStore.visibility = View.VISIBLE
        }

        binding.buttonChoiceDeleteStore.setOnClickListener {
            openDeleteStoreDialog()
        }
    }

    private fun openDeleteStoreDialog() {
        val deleteMessage = "매장을 삭제하시겠습니까?"
        MaterialAlertDialogBuilder(this)
            .setMessage(deleteMessage)
            .setNegativeButton("아니오")
            { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("예")
            { dialog, _ ->
                // 매장 삭제 기능 구현
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun onClick(view: View) {
    }
}