package myapplication.second.workinghourmanagement.store

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import myapplication.second.workinghourmanagement.databinding.DialogFragmentBottomSheetSetTimeBinding

class BottomSheetSetTimeDialogFragment: BottomSheetDialogFragment() {
    private var _binding: DialogFragmentBottomSheetSetTimeBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFragmentBottomSheetSetTimeBinding.inflate(inflater, container, false)

        setupListeners()

        return binding.root
    }

    private fun setupListeners() {
        binding.ivBack.setOnClickListener {
            requireActivity().finish()
        }

        binding.btnConfirm.setOnClickListener {
            // TODO: 시간을 선택하여 확인 버튼을 눌렀을 때 결과를 구현
        }
    }

    companion object {
        fun getIntent(context: Context) =
            Intent(context, BottomSheetSetTimeDialogFragment::class.java)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}