package myapplication.second.workinghourmanagement.store

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.RetrofitManager
import myapplication.second.workinghourmanagement.RetrofitService
import myapplication.second.workinghourmanagement.databinding.ActivityOwnerStoreInfoModificationBinding
import myapplication.second.workinghourmanagement.dto.store.ResponseGetStore
import myapplication.second.workinghourmanagement.dto.store.ResponseModifyStore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OwnerStoreInfoModificationActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityOwnerStoreInfoModificationBinding
    private lateinit var service: RetrofitService

    private var isTextNotEmpty: Boolean = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_owner_store_info_modification)
        service = RetrofitManager.retrofit.create(RetrofitService::class.java)

        binding.lifecycleOwner = this

        if (checkEditTextNotEmpty()) {
            binding.btnModifyStore.isClickable = true
        }

        setupListeners()
    }

    private fun setupListeners() {
        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnSearchPrimaryAddress.setOnClickListener {
            intentSearchPrimaryAddress()
        }

        binding.btnClassifyBusinessType.setOnClickListener {
            val bottomSheetOwnerStoreTypeDialog = BottomSheetOwnerStoreType()
            bottomSheetOwnerStoreTypeDialog.show(supportFragmentManager, bottomSheetOwnerStoreTypeDialog.tag)
        }

        binding.btnModifyCommuteInfo.setOnClickListener {
            val bottomSheetOwnerCommuteInfoDialog = BottomSheetOwnerCommuteInfo()
            bottomSheetOwnerCommuteInfoDialog.show(supportFragmentManager, bottomSheetOwnerCommuteInfoDialog.tag)
        }

        binding.btnModifyStore.setOnClickListener {
//            val responseGetStore = service.getStoreList().
//            modifyStoreInfo(responseGetStore)
            finish()
        }
    }

    // TODO: 매장 정보 수정 기능 구현
    private fun modifyStoreInfo(responseGetStore: ResponseGetStore) {
        val storeId = responseGetStore.storeId

        val storeInfo = HashMap<String, String>()

        storeInfo["branchName"] = binding.editBranchName.text.toString()
        storeInfo["detailAddress"] = binding.editDetailedAddress.text.toString()
        storeInfo["earlyLeaveTime"] = binding.tvSetAllowEarlyLeave.text.toString()
        storeInfo["primaryAddress"] = binding.editPrimaryAddress.text.toString()
        storeInfo["storeClassification"] = binding.editClassifyBusinessType.text.toString()
        storeInfo["storeName"] = binding.editStoreName.text.toString()
        storeInfo["lateTime"] = binding.tvSetAllowLateTime.text.toString()

        service.modifyStore(storeId, storeInfo)
            .enqueue(object : Callback<ResponseModifyStore> {
                override fun onResponse(
                    call: Call<ResponseModifyStore>,
                    response: Response<ResponseModifyStore>
                ) {
                    val body = response.body()
                    if (response.isSuccessful) {
                        if (body != null) {
                            Log.d("data", body.data)
                            Log.d("statusCode", body.statusCode.toString())
                            Log.d("message", body.message)

                            finish()
                            onModificationSuccess()
                        }
                    } else if (body?.statusCode == 401) {
                        Toast.makeText(
                            this@OwnerStoreInfoModificationActivity,
                            "Unauthorized",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (body?.statusCode == 403) {
                        Toast.makeText(
                            this@OwnerStoreInfoModificationActivity,
                            "Forbidden",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (body?.statusCode == 404) {
                        Toast.makeText(
                            this@OwnerStoreInfoModificationActivity,
                            "Not Found",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        return
                    }
                }

                override fun onFailure(call: Call<ResponseModifyStore>, t: Throwable) {
                    Log.e("fail", "store modification failed... Why? : " + t.message.orEmpty())
                }
            })
    }

    private fun onModificationSuccess() {
        val bottomSheetDialogFragment = BottomSheetOwnerCompletedStoreModification()
        bottomSheetDialogFragment.show(supportFragmentManager, bottomSheetDialogFragment.tag)
    }

    private fun intentSearchPrimaryAddress() {
        val intent = OwnerSearchPrimaryAddressActivity.getIntent(this)
        startActivity(intent)
    }

    companion object {
        fun getIntent(context: Context) =
            Intent(context, OwnerStoreInfoModificationActivity::class.java)
    }

    private fun checkEditTextNotEmpty(): Boolean {
        binding.editStoreName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                isTextNotEmpty = binding.editStoreName.text?.isNotEmpty() != true
            }
        })

        binding.editBranchName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                isTextNotEmpty = binding.editBranchName.text?.isNotEmpty() != true
            }
        })

        binding.editPrimaryAddress.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                isTextNotEmpty = binding.editPrimaryAddress.text?.isNotEmpty() != true
            }
        })

        binding.editDetailedAddress.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                isTextNotEmpty = binding.editDetailedAddress.text?.isNotEmpty() != true
            }
        })

        binding.editClassifyBusinessType.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                isTextNotEmpty = binding.editClassifyBusinessType.text?.isNotEmpty() != true
            }
        })

        return isTextNotEmpty
    }

    override fun onClick(view: View) {
    }
}