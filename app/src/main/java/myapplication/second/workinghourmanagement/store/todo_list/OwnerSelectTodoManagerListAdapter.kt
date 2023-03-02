package myapplication.second.workinghourmanagement.store.todo_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import myapplication.second.workinghourmanagement.databinding.ItemTodoStaffBinding
import myapplication.second.workinghourmanagement.dto.manageStaff.Staff

class OwnerSelectTodoManagerListAdapter(
    private val onClick: (Staff) -> Unit
): ListAdapter<Staff, OwnerSelectTodoManagerListAdapter.OwnerChooseTodoManagerListViewHolder>(
    DiffCallback()
) {

    private val staffList = mutableListOf<Staff>()

    class OwnerChooseTodoManagerListViewHolder(
        val binding: ItemTodoStaffBinding,
        private val onClick: (Staff) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(staffItem: Staff) {

            binding.root.setOnClickListener {
                onClick(staffItem)
            }

            binding.staffItem = staffItem
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OwnerChooseTodoManagerListViewHolder {
        val binding = ItemTodoStaffBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return OwnerChooseTodoManagerListViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: OwnerChooseTodoManagerListViewHolder, position: Int) {
        val staffItem = staffList[position]

        holder.bind(staffItem)
    }

    override fun getItemCount(): Int {
        return staffList.size
    }

    fun setStaffList(staffList: List<Staff>) {
//        staffList.clear()
//        staffList.addAll(staffList)
        notifyDataSetChanged()
    }

    fun add(position: Int, staff: Staff) {
        staffList.add(position, staff)
        notifyItemInserted(position)
    }

    fun replaceItem(staff: Staff) {
        val index = staffList.indexOf(staff)
        staffList[index] = staff
        notifyItemChanged(index)
    }

    private class DiffCallback : DiffUtil.ItemCallback<Staff>(){
        override fun areItemsTheSame(oldItem: Staff, newItem: Staff): Boolean {
            return oldItem.employmentId == newItem.employmentId
        }

        override fun areContentsTheSame(oldItem: Staff, newItem: Staff): Boolean {
            return oldItem == newItem
        }
    }
}