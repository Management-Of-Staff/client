package myapplication.second.workinghourmanagement.manageStaff

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import myapplication.second.workinghourmanagement.databinding.ItemWorkScheduleBinding
import myapplication.second.workinghourmanagement.dto.manageStaff.WorkTime

class WorkScheduleAdapter :
    ListAdapter<WorkTime, WorkScheduleAdapter.WorkScheduleViewHolder>(DiffCallback()) {
    private lateinit var binding: ItemWorkScheduleBinding

    inner class WorkScheduleViewHolder(binding: ItemWorkScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(workItem: WorkTime) {
            val day = when(workItem.dayOfWeek){
                "MONDAY" -> "월"
                "TUESDAY" -> "화"
                "WEDNESDAY" -> "수"
                "THURSDAY" -> "목"
                "FRIDAY" -> "금"
                "SATURDAY" -> "토"
                "SUNDAY" -> "일"
                else -> "없음"
            }
            binding.workingDay.text = day
            binding.workingTime.text = "${workItem.startTime.substring(0..4)} - ${workItem.endTime.substring(0..4)}"
        }
    }

    override fun onBindViewHolder(holder: WorkScheduleViewHolder, position: Int) {
        val workItem = currentList[position]
        holder.bind(workItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkScheduleViewHolder {
        binding = ItemWorkScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WorkScheduleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    private class DiffCallback : DiffUtil.ItemCallback<WorkTime>() {
        override fun areContentsTheSame(oldItem: WorkTime, newItem: WorkTime): Boolean {
            return oldItem.workTimeId == newItem.workTimeId
        }

        override fun areItemsTheSame(oldItem: WorkTime, newItem: WorkTime): Boolean {
            return oldItem == newItem
        }
    }
}