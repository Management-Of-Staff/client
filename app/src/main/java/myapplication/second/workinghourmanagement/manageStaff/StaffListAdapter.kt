package myapplication.second.workinghourmanagement.manageStaff

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.databinding.ItemListStaffBinding
import myapplication.second.workinghourmanagement.dto.manageStaff.Staff

class StaffListAdapter(private val clickListener: OnStaffClickListener) :
    ListAdapter<Staff, StaffListAdapter.StaffViewHolder>(DiffCallback()) {
    private lateinit var binding: ItemListStaffBinding

    inner class StaffViewHolder(
        binding: ItemListStaffBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(staffItem: Staff) {
            binding.tvStaffName.text = staffItem.staffName
            //fixme response 데이터 넣기
            binding.tvStaffAttendance.text = staffItem.workingStatus
            binding.imgView.setImageResource(R.drawable.default_profile)
            //todo 근무시간
        }
    }


    interface OnStaffClickListener {
        fun onClick(item: Staff, position: Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffViewHolder {
        binding = ItemListStaffBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StaffViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StaffViewHolder, position: Int) {
        val staffItem = currentList[position]
        holder.bind(staffItem)
        binding.root.setOnClickListener {
            clickListener.onClick(staffItem, position)
        }
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