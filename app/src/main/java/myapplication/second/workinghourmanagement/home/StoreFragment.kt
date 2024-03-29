package myapplication.second.workinghourmanagement.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import myapplication.second.workinghourmanagement.databinding.FragmentOwnerStoreBinding
import myapplication.second.workinghourmanagement.manageStaff.StaffListActivity
import myapplication.second.workinghourmanagement.store.schedule.OwnerImportantScheduleActivity
import myapplication.second.workinghourmanagement.store.notice.OwnerNoticeListActivity
import myapplication.second.workinghourmanagement.store.todo_list.OwnerStaffTodoListManagementActivity

class StoreFragment: Fragment() {
    private lateinit var binding: FragmentOwnerStoreBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOwnerStoreBinding.inflate(layoutInflater)

        setupListeners()

        return binding.root
    }

    private fun setupListeners() {
        binding.btnStoreNoticeList.setOnClickListener {
            val intent = OwnerNoticeListActivity.getIntent(requireActivity())
            startActivity(intent)
        }

        binding.btnManageStaffTodo.setOnClickListener {
            val intent = OwnerStaffTodoListManagementActivity.getIntent(requireActivity())
            startActivity(intent)
        }

        binding.btnStoreSchedule.setOnClickListener {
            val intent = OwnerImportantScheduleActivity.getIntent(requireActivity())
            startActivity(intent)
        }

        binding.btnManageStaffInfo.setOnClickListener {
            val intent = StaffListActivity.getIntent(requireActivity())
            startActivity(intent)
        }
    }

    companion object{
        fun newInstance(): StoreFragment{
            return StoreFragment()
        }
    }
}