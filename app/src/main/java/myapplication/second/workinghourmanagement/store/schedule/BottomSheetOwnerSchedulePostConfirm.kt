package myapplication.second.workinghourmanagement.store.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import myapplication.second.workinghourmanagement.databinding.BottomSheetSchedulePostConfirmBinding

class BottomSheetOwnerSchedulePostConfirm: BottomSheetDialogFragment(), View.OnClickListener {

    private var _binding: BottomSheetSchedulePostConfirmBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetSchedulePostConfirmBinding.inflate(inflater, container, false)

        setupListeners()

        return binding.root
    }

    private fun setupListeners() {
        binding.btnConfirm.setOnClickListener {
            requireActivity().finish()
        }
    }

    override fun onClick(view: View) {
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}