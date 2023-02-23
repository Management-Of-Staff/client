package myapplication.second.workinghourmanagement.store.todo_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import myapplication.second.workinghourmanagement.databinding.ItemOwnerStaffTodoManagementBinding
import myapplication.second.workinghourmanagement.dto.todo_list.ResponseGetStaffTodo

class OwnerStaffTodoListManagementAdapter(
    private val onClick: (ResponseGetStaffTodo) -> Unit
): ListAdapter<ResponseGetStaffTodo, OwnerStaffTodoListManagementAdapter.StaffTodoListManagementViewHolder>(
    DiffCallback()
) {

    private val staffTodoList = mutableListOf<ResponseGetStaffTodo>()
    private var isRadioButtonVisible = false

    class StaffTodoListManagementViewHolder(
        val binding: ItemOwnerStaffTodoManagementBinding,
        private val onClick: (ResponseGetStaffTodo) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(todoItem: ResponseGetStaffTodo) {

            binding.root.setOnClickListener {
                onClick(todoItem)
            }

            binding.todoItem = todoItem
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffTodoListManagementViewHolder {
        val binding = ItemOwnerStaffTodoManagementBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return StaffTodoListManagementViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: StaffTodoListManagementViewHolder, position: Int) {
        val ownerItem = staffTodoList[position]

        holder.bind(ownerItem)
    }

    override fun getItemCount(): Int {
        return staffTodoList.size
    }

    fun setStaffTodoList(store: List<ResponseGetStaffTodo>) {
        staffTodoList.clear()
        staffTodoList.addAll(store)
        notifyDataSetChanged()
    }

    fun add(position: Int, store: ResponseGetStaffTodo) {
        staffTodoList.add(position, store)
        notifyItemInserted(position)
    }

    fun replaceItem(store: ResponseGetStaffTodo) {
        val index = staffTodoList.indexOf(store)
        staffTodoList[index] = store
        notifyItemChanged(index)
    }

    fun toggleRadioButtonVisibility() {
        isRadioButtonVisible = !isRadioButtonVisible
        notifyDataSetChanged()
    }

    private class DiffCallback : DiffUtil.ItemCallback<ResponseGetStaffTodo>(){
        override fun areItemsTheSame(oldItem: ResponseGetStaffTodo, newItem: ResponseGetStaffTodo): Boolean {
            return oldItem.data == newItem.data
        }

        override fun areContentsTheSame(oldItem: ResponseGetStaffTodo, newItem: ResponseGetStaffTodo): Boolean {
            return oldItem == newItem
        }
    }
}