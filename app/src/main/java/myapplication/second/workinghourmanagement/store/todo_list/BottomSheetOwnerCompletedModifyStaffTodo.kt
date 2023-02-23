package myapplication.second.workinghourmanagement.store.todo_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import myapplication.second.workinghourmanagement.databinding.BottomSheetOwnerModifyStaffTodoCompletedBinding

class BottomSheetOwnerCompletedModifyStaffTodo: BottomSheetDialogFragment(), View.OnClickListener {
    private var _binding: BottomSheetOwnerModifyStaffTodoCompletedBinding? = null
    private val binding: BottomSheetOwnerModifyStaffTodoCompletedBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetOwnerModifyStaffTodoCompletedBinding.inflate(inflater, container, false)

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
}