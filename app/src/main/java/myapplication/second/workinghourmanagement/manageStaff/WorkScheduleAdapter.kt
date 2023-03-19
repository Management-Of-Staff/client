package myapplication.second.workinghourmanagement.manageStaff

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import myapplication.second.workinghourmanagement.databinding.ItemWorkScheduleBinding
import myapplication.second.workinghourmanagement.dto.manageStaff.WorkTimeRequest

class WorkScheduleAdapter :
    ListAdapter<WorkTimeRequest, WorkScheduleAdapter.WorkScheduleViewHolder>(DiffCallback()) {
    private lateinit var binding: ItemWorkScheduleBinding

    inner class WorkScheduleViewHolder(binding: ItemWorkScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(workItem: WorkTimeRequest) {
            var day = ""
            for(i in workItem.dayOfWeekList){
                when(i){
                    "MONDAY" -> day += "월, "
                    "TUESDAY" -> day += "화, "
                    "WEDNESDAY" -> day += "수, "
                    "THURSDAY" -> day += "목, "
                    "FRIDAY" -> day += "금, "
                    "SATURDAY" -> day += "토, "
                    "SUNDAY" -> day += "일, "
                }
            }
            binding.workingDay.text = day.substring(0 until day.length-2)
            binding.startTime.text = workItem.startTime.substring(0..4) + " - "
            binding.endTime.text = workItem.endTime.substring(0..4)
//            binding.workingDay.text = workItem.dayOfWeekList
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

    private class DiffCallback : DiffUtil.ItemCallback<WorkTimeRequest>() {
        override fun areContentsTheSame(
            oldItem: WorkTimeRequest,
            newItem: WorkTimeRequest
        ): Boolean {
            return oldItem.dayOfWeekList == newItem.dayOfWeekList
        }

        override fun areItemsTheSame(oldItem: WorkTimeRequest, newItem: WorkTimeRequest): Boolean {
            return oldItem == newItem
        }
    }
}