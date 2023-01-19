package myapplication.second.workinghourmanagement.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import myapplication.second.workinghourmanagement.databinding.BottomSheetOwnerStoreTypeBinding

class BottomSheetOwnerStoreType: BottomSheetDialogFragment(), View.OnClickListener {
    private var _binding: BottomSheetOwnerStoreTypeBinding? = null
    private val binding: BottomSheetOwnerStoreTypeBinding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetOwnerStoreTypeBinding.inflate(inflater, container, false)

        setupListeners()

        return binding.root
    }

    private fun setupStoreTypeListener(gridLayout: GridLayout) {
        val totalCount = gridLayout.childCount

        for (i: Int in 0 until totalCount) {
            val storeType = gridLayout.getChildAt(i)
            storeType.setOnClickListener {

            }
        }
    }

    private fun setupListeners() {
        setupStoreTypeListener(binding.gridLayoutStoreType)

        binding.btnChooseStoreType.setOnClickListener {
            // TODO: 사업장 분류 확인 버튼 클릭시 API 연동
        }
    }

    override fun onClick(view: View) {
    }
}