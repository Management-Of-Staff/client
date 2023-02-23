package myapplication.second.workinghourmanagement.store

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.RetrofitManager
import myapplication.second.workinghourmanagement.RetrofitService
import myapplication.second.workinghourmanagement.databinding.ActivityOwnerStoreConversionBinding
import myapplication.second.workinghourmanagement.dto.store.ResponseGetStore
import myapplication.second.workinghourmanagement.dto.store.ResponseGetStoreList
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
        getOwnerStoreList()
    }

    private fun getOwnerStoreList() {
        service.getStoreList()
            .enqueue(object : Callback<ResponseGetStoreList> {
                override fun onResponse(
                    call: Call<ResponseGetStoreList>,
                    response: Response<ResponseGetStoreList>
                ) {
                    val body = response.body()
                    if (response.isSuccessful) {
                        body?.let {
                            binding.tvStoreTitle.text = body.data.firstOrNull()?.storeName
                            binding.tvStoreSubtitle.text = body.data.firstOrNull()?.branchName
                            initRecyclerView(body.data, binding.rvStoreList)
                            Log.d("TAG", "onResponse: " + body.message)
                            Log.d("TAG", "onResponse: " + body.data)
                        }
                    } else if (body?.statusCode == 401) {
                        Toast.makeText(
                            this@OwnerStoreConversionActivity,
                            "Unauthorized",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (body?.statusCode == 403) {
                        Toast.makeText(
                            this@OwnerStoreConversionActivity,
                            "Forbidden",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (body?.statusCode == 404) {
                        Toast.makeText(
                            this@OwnerStoreConversionActivity,
                            "Not Found",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        return
                    }
                }

                override fun onFailure(call: Call<ResponseGetStoreList>, t: Throwable) {
                    Log.e("fail", "get store list failed... Why? : " + t.message.orEmpty())
                }
            })
    }

    private fun initRecyclerView(storeList: List<ResponseGetStore>, recyclerView: RecyclerView) {
        ownerStoreConversionAdapter = OwnerStoreConversionAdapter(
            onClick = ::showStoreDetail
        )

        recyclerView.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = ownerStoreConversionAdapter
        }

        ownerStoreConversionAdapter.submitList(storeList)
        Log.d("TAG", "initRecyclerView: " + ownerStoreConversionAdapter.currentList)
    }

    private fun showStoreDetail(responseGetStore: ResponseGetStore) {
//        val intent = StoreDetailActivity.getIntent(this, resultGetStore.storeId)
//        startActivity(intent)
    }

    private fun setupListener() {
        binding.ivBack.setOnClickListener {
            finish()
        }

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

            ownerStoreConversionAdapter.toggleRadioButtonVisibility()
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
                val responseStore = service.getStoreList()
//                deleteStore(respon)
                dialog.dismiss()
            }
            .create()
            .show()
    }

    // TODO: 매장 삭제 기능 구현
    private fun deleteStore(responseGetStore: ResponseGetStore) {
        val storeId = responseGetStore.storeId

        service.deleteStore(storeId)
            .enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null) {
                            onDeleteSuccess()
                        }
                    } else if (response.code() == 401) {
                        Toast.makeText(
                            this@OwnerStoreConversionActivity,
                            "Unauthorized",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (response.code() == 403) {
                        Toast.makeText(
                            this@OwnerStoreConversionActivity,
                            "Forbidden",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (response.code() == 404) {
                        Toast.makeText(
                            this@OwnerStoreConversionActivity,
                            "Not Found",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        return
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    Log.e("fail", "store delete failed... Why? : " + t.message.orEmpty())
                }

            })
    }

    private fun onDeleteSuccess() {
        Toast.makeText(
            this@OwnerStoreConversionActivity,
            "삭제했습니다.",
            Toast.LENGTH_SHORT
        ).show()    }

    companion object {
        fun getIntent(context: Context) =
            Intent(context, OwnerStoreConversionActivity::class.java)
    }

    override fun onClick(view: View) {
    }
}