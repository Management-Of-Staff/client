package myapplication.second.workinghourmanagement.store.todo_list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import myapplication.second.workinghourmanagement.databinding.BottomSheetOwnerSelectTodoManagerBinding
import myapplication.second.workinghourmanagement.dto.ResultGetStaff

class BottomSheetOwnerSelectTodoManager: BottomSheetDialogFragment() {
    private var _binding: BottomSheetOwnerSelectTodoManagerBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var ownerSelectTodoManagerListAdapter: OwnerSelectTodoManagerListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetOwnerSelectTodoManagerBinding.inflate(inflater, container, false)

        initRecyclerView(binding.rvStaffList)

        setupListeners()

        return binding.root
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        ownerSelectTodoManagerListAdapter = OwnerSelectTodoManagerListAdapter(
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
            adapter = ownerSelectTodoManagerListAdapter
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
            Intent(context, BottomSheetOwnerSelectTodoManagerBinding::class.java)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}