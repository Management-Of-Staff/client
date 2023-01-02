package myapplication.second.workinghourmanagement.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import myapplication.second.workinghourmanagement.databinding.DialogFragmentBottomSheetOwnerCommuteInfoBinding

class BottomSheetOwnerCommuteInfoDialogFragment: BottomSheetDialogFragment(), View.OnClickListener {
    private var _binding: DialogFragmentBottomSheetOwnerCommuteInfoBinding? = null
    private val binding: DialogFragmentBottomSheetOwnerCommuteInfoBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogFragmentBottomSheetOwnerCommuteInfoBinding.inflate(inflater, container, false)

        setupListeners()

        return binding.root
    }

    private fun setupListeners() {
    }

    override fun onClick(view: View) {
    }
}