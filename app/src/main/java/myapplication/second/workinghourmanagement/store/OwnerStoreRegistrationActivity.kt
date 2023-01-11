package myapplication.second.workinghourmanagement.store

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.RetrofitManager
import myapplication.second.workinghourmanagement.RetrofitService
import myapplication.second.workinghourmanagement.databinding.ActivityOwnerStoreRegistrationBinding
import myapplication.second.workinghourmanagement.dto.ResultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OwnerStoreRegistrationActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityOwnerStoreRegistrationBinding
    private lateinit var service: RetrofitService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_owner_store_registration)
        service = RetrofitManager.retrofit.create(RetrofitService::class.java)

        binding.lifecycleOwner = this


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

        binding.btnRegisterStore.setOnClickListener {
            openRegisterStoreDialog()
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
        val storeInfo = HashMap<String, String>()

        storeInfo["branchName"] = binding.editBranchName.text.toString()
        storeInfo["detailAddress"] = binding.editDetailedAddress.text.toString()
        storeInfo["earlyLeaveTime"] = binding.tvSetAllowEarlyLeave.text.toString()
        storeInfo["ownerId"] = "1"
        storeInfo["primaryAddress"] = binding.editPrimaryAddress.text.toString()
        storeInfo["storeClassification"] = binding.editClassifyBusinessType.text.toString()
        storeInfo["storeName"] = binding.editStoreName.text.toString()
        storeInfo["lateTime"] = binding.tvSetAllowLateTime.text.toString()

        service.postStore(storeInfo)
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

                            if (body.statusCode == 200) onRegistrationSuccess()
                        }
                    } else {
                        return
                    }
                }

                override fun onFailure(call: Call<ResultResponse>, t: Throwable) {
                    Log.d("registration fail", "[Fail]$t")
                }
            })
    }

    private fun onRegistrationSuccess() {
        val bottomSheetDialogFragment = BottomSheetOwnerCompletedStoreRegistrationDialogFragment()
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

    override fun onClick(view: View) {
    }
}