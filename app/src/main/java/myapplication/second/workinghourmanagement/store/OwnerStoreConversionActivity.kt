package myapplication.second.workinghourmanagement.store

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.RetrofitManager
import myapplication.second.workinghourmanagement.RetrofitService
import myapplication.second.workinghourmanagement.databinding.ActivityOwnerStoreConversionBinding
import myapplication.second.workinghourmanagement.dto.ResultGetStore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OwnerStoreConversionActivity: AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityOwnerStoreConversionBinding
    private lateinit var ownerStoreConversionAdapter: OwnerStoreConversionAdapter
    private lateinit var service: RetrofitService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_owner_store_conversion)
        service = RetrofitManager.retrofit.create(RetrofitService::class.java)

        binding.lifecycleOwner = this

        setupView()
        setupListener()
    }

    private fun setupView() {
        val myIntent = intent
        val ownerId = myIntent.getStringExtra("uuid").orEmpty()

        val storeInfo = HashMap<String, String>()

        storeInfo["storeId"] = "1"
        storeInfo["storeName"] = "string"
        storeInfo["primaryAddress"] = "string"

        service.getStoreList(ownerId)
            .enqueue(object : Callback<List<ResultGetStore>> {
                override fun onResponse(
                    call: Call<List<ResultGetStore>>,
                    response: Response<List<ResultGetStore>>
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        body?.let {
                            initRecyclerView(binding.rvStoreList)
                        }
                    } else {
                        return
                    }
                }

                override fun onFailure(call: Call<List<ResultGetStore>>, t: Throwable) {
                    Log.d("getStoreList fail", "[Fail]$t")
                }
            })
        initRecyclerView(binding.rvStoreList)
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        ownerStoreConversionAdapter = OwnerStoreConversionAdapter(
            onClick = ::showStoreDetail
        )

        recyclerView.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = ownerStoreConversionAdapter
        }
    }

    private fun showStoreDetail(resultGetStore: ResultGetStore) {
//        val intent = StoreDetailActivity.getIntent(this, resultGetStore.storeId)
//        startActivity(intent)
    }

    private fun setupListener() {
        binding.btnOptionMenu.setOnClickListener {
            intentModifyStore()
        }

        binding.btnRegisterStore.setOnClickListener {
            intentRegisterStore()
        }

        binding.btnDeleteStore.setOnClickListener {
            binding.tvTitleConvertStore.text = getString(R.string.delete_store)

            binding.btnRegisterStore.visibility = View.GONE
            binding.btnDeleteStore.visibility = View.GONE
            binding.buttonChoiceDeleteStore.visibility = View.VISIBLE
        }

        binding.buttonChoiceDeleteStore.setOnClickListener {
            openDeleteStoreDialog()
        }
    }

    private fun intentRegisterStore() {
        val intent = OwnerStoreRegistrationActivity.getIntent(this)
        startActivity(intent)
    }


    private fun intentModifyStore() {
        val intent = OwnerStoreInfoModificationActivity.getIntent(this)
        startActivity(intent)
    }

    private fun openDeleteStoreDialog() {
        val deleteMessage = getString(R.string.do_you_want_to_delete_store)
        MaterialAlertDialogBuilder(this)
            .setMessage(deleteMessage)
            .setNegativeButton(getString(R.string.no))
            { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(getString(R.string.yes))
            { dialog, _ ->
                // TODO: 매장 삭제 기능 구현
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun onClick(view: View) {
    }
}