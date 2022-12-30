package myapplication.second.workinghourmanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import myapplication.second.workinghourmanagement.databinding.ActivityOwnerStoreRegistrationBinding

class OwnerStoreRegistrationActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityOwnerStoreRegistrationBinding
    private val bottomSheetOwnerStoreTypeDialog = BottomSheetDialog(this)
    private val bottomSheetOwnerCommuteInfoDialog = BottomSheetDialog(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_owner_store_registration)

        binding.lifecycleOwner = this

        setupListeners()

        val bottomSheetOwnerStoreTypeView = layoutInflater.inflate(R.layout.dialog_bottom_sheet_owner_store_type, null)
        bottomSheetOwnerStoreTypeDialog.setContentView(bottomSheetOwnerStoreTypeView)

        val bottomSheetOwnerCommuteInfoView = layoutInflater.inflate(R.layout.dialog_bottom_sheet_owner_commute_info, null)
        bottomSheetOwnerCommuteInfoDialog.setContentView(bottomSheetOwnerCommuteInfoView)
    }

    private fun setupListeners() {
        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnSearchBaseAddress.setOnClickListener {
            intentSearchBaseAddress()
        }

        binding.btnClassifyBusinessType.setOnClickListener {
            bottomSheetOwnerStoreTypeDialog.show()
        }

        binding.btnModifyCommuteInfo.setOnClickListener {
            bottomSheetOwnerCommuteInfoDialog.show()
        }

        binding.btnRegisterStore.setOnClickListener {
            openRegisterStoreDialog()
        }
    }

    private fun openRegisterStoreDialog() {
        val registerMessage = "매장을 등록하시겠습니까?"
        MaterialAlertDialogBuilder(this)
            .setMessage(registerMessage)
            .setNegativeButton("아니오")
            { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("예")
            { dialog, _ ->
                // 매장 등록 기능 구현
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun intentSearchBaseAddress() {
        val intent = OwnerSearchPrimaryAddressActivity.getIntent(this)
        startActivity(intent)
    }

    override fun onClick(view: View) {

    }
}