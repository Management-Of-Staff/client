package myapplication.second.workinghourmanagement.store.todo_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import myapplication.second.workinghourmanagement.databinding.ItemTodoStaffBinding
import myapplication.second.workinghourmanagement.dto.ResultGetStaff

class OwnerSelectTodoManagerListAdapter(
    private val onClick: (ResultGetStaff) -> Unit
): ListAdapter<ResultGetStaff, OwnerSelectTodoManagerListAdapter.OwnerChooseTodoManagerListViewHolder>(
    DiffCallback()
) {

    private val staffList = mutableListOf<ResultGetStaff>()

    class OwnerChooseTodoManagerListViewHolder(
        val binding: ItemTodoStaffBinding,
        private val onClick: (ResultGetStaff) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(staffItem: ResultGetStaff) {

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

    fun setStaffList(staffList: List<ResultGetStaff>) {
//        staffList.clear()
//        staffList.addAll(staffList)
        notifyDataSetChanged()
    }

    fun add(position: Int, staff: ResultGetStaff) {
        staffList.add(position, staff)
        notifyItemInserted(position)
    }

    fun replaceItem(staff: ResultGetStaff) {
        val index = staffList.indexOf(staff)
        staffList[index] = staff
        notifyItemChanged(index)
    }

    private class DiffCallback : DiffUtil.ItemCallback<ResultGetStaff>(){
        override fun areItemsTheSame(oldItem: ResultGetStaff, newItem: ResultGetStaff): Boolean {
            return oldItem.staffId == newItem.staffId
        }

        override fun areContentsTheSame(oldItem: ResultGetStaff, newItem: ResultGetStaff): Boolean {
            return oldItem == newItem
        }
    }
}