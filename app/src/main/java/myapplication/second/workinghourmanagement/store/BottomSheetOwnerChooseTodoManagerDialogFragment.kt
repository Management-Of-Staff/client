package myapplication.second.workinghourmanagement.store

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import myapplication.second.workinghourmanagement.databinding.DialogFragmentBottomSheetOwnerChooseTodoManagerBinding
import myapplication.second.workinghourmanagement.dto.ResultGetStaff

class BottomSheetOwnerChooseTodoManagerDialogFragment: BottomSheetDialogFragment() {
    private var _binding: DialogFragmentBottomSheetOwnerChooseTodoManagerBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var ownerChooseTodoManagerListAdapter: OwnerChooseTodoManagerListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFragmentBottomSheetOwnerChooseTodoManagerBinding.inflate(inflater, container, false)

        initRecyclerView(binding.rvStaffList)

        setupListeners()

        return binding.root
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        ownerChooseTodoManagerListAdapter = OwnerChooseTodoManagerListAdapter(
            onClick = ::addManagers
        )

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 3).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return 1 // 1개의 인덱스가 가질 부피
                    }
                }
            }
            adapter = ownerChooseTodoManagerListAdapter
        }
    }

    private fun addManagers(resultGetStaff: ResultGetStaff) {

    }

    private fun setupListeners() {
        binding.btnChooseTodoManager.setOnClickListener {
            // TODO: 시간을 선택하여 확인 버튼을 눌렀을 때 결과를 구현
        }
    }

    companion object {
        fun getIntent(context: Context) =
            Intent(context, DialogFragmentBottomSheetOwnerChooseTodoManagerBinding::class.java)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}