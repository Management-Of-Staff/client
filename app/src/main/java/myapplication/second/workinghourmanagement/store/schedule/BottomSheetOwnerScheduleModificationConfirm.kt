package myapplication.second.workinghourmanagement.store.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import myapplication.second.workinghourmanagement.databinding.BottomSheetScheduleModificationConfirmBinding

class BottomSheetOwnerScheduleModificationConfirm: BottomSheetDialogFragment(), View.OnClickListener {

    private var _binding: BottomSheetScheduleModificationConfirmBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetScheduleModificationConfirmBinding.inflate(inflater, container, false)

        setupListeners()

        return binding.root
    }

    private fun setupListeners() {
        binding.btnConfirm.setOnClickListener {
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onClick(view: View) {
    }
}