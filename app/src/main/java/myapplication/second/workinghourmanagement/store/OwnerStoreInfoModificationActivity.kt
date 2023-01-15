package myapplication.second.workinghourmanagement.store

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import myapplication.second.workinghourmanagement.MyApplication
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.RetrofitManager
import myapplication.second.workinghourmanagement.RetrofitService
import myapplication.second.workinghourmanagement.databinding.ActivityOwnerStoreInfoModificationBinding
import myapplication.second.workinghourmanagement.dto.ResultResponse
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
            val bottomSheetOwnerStoreTypeDialog = BottomSheetOwnerStoreTypeDialogFragment()
            bottomSheetOwnerStoreTypeDialog.show(supportFragmentManager, bottomSheetOwnerStoreTypeDialog.tag)
        }

        binding.btnModifyCommuteInfo.setOnClickListener {
            val bottomSheetOwnerCommuteInfoDialog = BottomSheetOwnerCommuteInfoDialogFragment()
            bottomSheetOwnerCommuteInfoDialog.show(supportFragmentManager, bottomSheetOwnerCommuteInfoDialog.tag)
        }

        binding.btnModifyStore.setOnClickListener {
            modifyStoreInfo()
            finish()
        }
    }

    // TODO: 매장 정보 수정 기능 구현
    private fun modifyStoreInfo() {
        val token = "Bearer " + MyApplication.prefs.getString("accessToken")

        val storeInfo = HashMap<String, String>()

        storeInfo["branchName"] = binding.editBranchName.text.toString()
        storeInfo["detailAddress"] = binding.editDetailedAddress.text.toString()
        storeInfo["earlyLeaveTime"] = binding.tvSetAllowEarlyLeave.text.toString()
        storeInfo["primaryAddress"] = binding.editPrimaryAddress.text.toString()
        storeInfo["storeClassification"] = binding.editClassifyBusinessType.text.toString()
        storeInfo["storeName"] = binding.editStoreName.text.toString()
        storeInfo["lateTime"] = binding.tvSetAllowLateTime.text.toString()

        service.postStore(token, storeInfo)
            .enqueue(object : Callback<ResultResponse> {
                override fun onResponse(
                    call: Call<ResultResponse>,
                    response: Response<ResultResponse>
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null) {
                            Log.d("data", body.data.toString())
                            Log.d("statusCode", body.statusCode.toString())
                            Log.d("message", body.message)

                            if (body.statusCode == 200) {
                                finish()
                                onModificationSuccess()
                            }
                        }
                    } else {
                        return
                    }
                }

                override fun onFailure(call: Call<ResultResponse>, t: Throwable) {
                    Log.e("fail", "store modification failed... Why? : " + t.message.orEmpty())
                }
            })
    }

    private fun onModificationSuccess() {
        val bottomSheetDialogFragment = BottomSheetOwnerCompletedStoreModificationDialogFragment()
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