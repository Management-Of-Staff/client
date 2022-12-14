package myapplication.second.workinghourmanagement.store

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.databinding.ActivityOwnerStoreInfoModificationBinding

class OwnerStoreInfoModificationActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityOwnerStoreInfoModificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_owner_store_info_modification)

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
                // TODO: ?????? ?????? ?????? ?????? ??????
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun intentSearchPrimaryAddress() {
        val intent = OwnerSearchPrimaryAddressActivity.getIntent(this)
        startActivity(intent)
    }

    companion object {
        fun getIntent(context: Context) =
            Intent(context, OwnerStoreInfoModificationActivity::class.java)
    }

    override fun onClick(view: View) {
    }
}