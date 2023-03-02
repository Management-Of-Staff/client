package myapplication.second.workinghourmanagement.manageStaff

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.databinding.ItemListStaffBinding
import myapplication.second.workinghourmanagement.dto.manageStaff.Staff

class StaffListAdapter : ListAdapter<Staff, StaffListAdapter.StaffViewHolder>(DiffCallback()) {
    class StaffViewHolder(
        val binding: ItemListStaffBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(staffItem: Staff) {
            binding.tvStaffName.text = staffItem.staffName
            //fixme response 데이터 넣기
            binding.tvStaffAttendance.text = "부재중"
            binding.imgView.setImageResource(R.drawable.default_profile)
            //todo 근무시간
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffViewHolder {
        return StaffViewHolder(
            ItemListStaffBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StaffViewHolder, position: Int) {
//        val staffItem = currentList[position]
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    private class DiffCallback : DiffUtil.ItemCallback<Staff>() {
        override fun areItemsTheSame(oldItem: Staff, newItem: Staff): Boolean {
            return oldItem.employmentId == newItem.employmentId
        }

        override fun areContentsTheSame(oldItem: Staff, newItem: Staff): Boolean {
            return oldItem == newItem
        }
    }
}