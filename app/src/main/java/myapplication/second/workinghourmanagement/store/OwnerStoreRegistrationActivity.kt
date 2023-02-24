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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import myapplication.second.workinghourmanagement.MyApplication
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.RetrofitManager
import myapplication.second.workinghourmanagement.RetrofitService
import myapplication.second.workinghourmanagement.databinding.ActivityOwnerStoreRegistrationBinding
import myapplication.second.workinghourmanagement.dto.store.ResponseRegisterStore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OwnerStoreRegistrationActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityOwnerStoreRegistrationBinding
    private lateinit var service: RetrofitService

    private var isTextNotEmpty: Boolean = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_owner_store_registration)
        service = RetrofitManager.retrofit.create(RetrofitService::class.java)

        binding.lifecycleOwner = this

        if (checkEditTextNotEmpty()) {
            binding.btnRegisterStore.isClickable = true
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

        binding.btnRegisterStore.setOnClickListener {
            if (binding.editStoreName.text?.isNotEmpty() == true &&
                binding.editBranchName.text?.isNotEmpty() == true &&
                binding.editPrimaryAddress.text?.isNotEmpty() == true &&
                binding.editDetailedAddress.text?.isNotEmpty() == true &&
                binding.editClassifyBusinessType.text?.isNotEmpty() == true) {

                openRegisterStoreDialog()
            }
        }
    }

    private fun openRegisterStoreDialog() {
        val registerMessage = getString(R.string.do_you_want_to_register_store)
        MaterialAlertDialogBuilder(this)
            .setMessage(registerMessage)
            .setNegativeButton(getString(R.string.no))
            { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(getString(R.string.yes))
            { dialog, _ ->
                // TODO: 매장 등록 기능 구현
                registerStore()
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun registerStore() {
        val token = MyApplication.prefs.getString("accessToken")

        val storeInfo = HashMap<String, String>()

        storeInfo["branchName"] = binding.editBranchName.text.toString()
        storeInfo["detailAddress"] = binding.editDetailedAddress.text.toString()
        storeInfo["earlyLeaveTime"] = binding.tvSetAllowEarlyLeave.text.toString()
        storeInfo["primaryAddress"] = binding.editPrimaryAddress.text.toString()
        storeInfo["storeClassification"] = binding.editClassifyBusinessType.text.toString()
        storeInfo["storeName"] = binding.editStoreName.text.toString()
        storeInfo["lateTime"] = binding.tvSetAllowLateTime.text.toString()

        service.postStore(storeInfo)
            .enqueue(object : Callback<ResponseRegisterStore> {
                override fun onResponse(
                    call: Call<ResponseRegisterStore>,
                    response: Response<ResponseRegisterStore>
                ) {
                    val body = response.body()
                    if (response.isSuccessful && body != null) {
                        Log.d("data", body.data)
                        Log.d("statusCode", body.statusCode.toString())
                        Log.d("message", body.message)

                        finish()
                        onRegistrationSuccess()
                    } else if (body?.statusCode == 401) {
                        Toast.makeText(
                            this@OwnerStoreRegistrationActivity,
                            "Unauthorized",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (body?.statusCode == 403) {
                        Toast.makeText(
                            this@OwnerStoreRegistrationActivity,
                            "Forbidden",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (body?.statusCode == 404) {
                        Toast.makeText(
                            this@OwnerStoreRegistrationActivity,
                            "Not Found",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        return
                    }
                }

                override fun onFailure(call: Call<ResponseRegisterStore>, t: Throwable) {
                    Log.e("fail", "store registration failed... Why? : " + t.message.orEmpty())
                }
            })
    }

    private fun onRegistrationSuccess() {
        val bottomSheetDialogFragment = BottomSheetOwnerCompletedStoreRegistration()
        bottomSheetDialogFragment.show(supportFragmentManager, bottomSheetDialogFragment.tag)
    }

    private fun intentSearchPrimaryAddress() {
        val intent = OwnerSearchPrimaryAddressActivity.getIntent(this)
        startActivity(intent)
    }

    companion object {
        fun getIntent(context: Context) =
            Intent(context, OwnerStoreRegistrationActivity::class.java)
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