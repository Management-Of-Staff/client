package myapplication.second.workinghourmanagement.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
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

    private fun setupAllowLateListener(gridLayout: GridLayout) {
        val totalCount = gridLayout.childCount

        for (i: Int in 0 until totalCount) {
            val storeType = gridLayout.getChildAt(i)
            storeType.setOnClickListener {

            }
        }
    }

    private fun setupAllowEarlyLeaveListener(gridLayout: GridLayout) {
        val totalCount = gridLayout.childCount

        for (i: Int in 0 until totalCount) {
            val storeType = gridLayout.getChildAt(i)
            storeType.setOnClickListener {

            }
        }
    }

    private fun setupListeners() {
        setupAllowLateListener(binding.gridLayoutAllowLate)
        setupAllowEarlyLeaveListener(binding.gridLayoutAllowEarlyLeave)

        binding.btnChooseCommuteInfo.setOnClickListener {
            // TODO: 사업장 분류 확인 버튼 클릭시 API 연동
        }
    }

    override fun onClick(view: View) {
    }
}