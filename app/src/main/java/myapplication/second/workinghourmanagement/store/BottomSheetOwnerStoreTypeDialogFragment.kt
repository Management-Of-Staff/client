package myapplication.second.workinghourmanagement.store

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import myapplication.second.workinghourmanagement.databinding.DialogFragmentBottomSheetOwnerStoreTypeBinding

class BottomSheetOwnerStoreTypeDialogFragment: BottomSheetDialogFragment(), View.OnClickListener {
    private var _binding: DialogFragmentBottomSheetOwnerStoreTypeBinding? = null
    private val binding: DialogFragmentBottomSheetOwnerStoreTypeBinding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFragmentBottomSheetOwnerStoreTypeBinding.inflate(inflater, container, false)

        setupListeners()

        return binding.root
    }

    private fun setupListeners() {
        binding.btnChooseStoreType.setOnClickListener {
            // TODO: 사업장 분류 확인 버튼 클릭시 API 연동
        }
    }

    override fun onClick(view: View) {
    }
}