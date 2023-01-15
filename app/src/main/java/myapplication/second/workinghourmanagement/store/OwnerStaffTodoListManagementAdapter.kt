package myapplication.second.workinghourmanagement.store

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import myapplication.second.workinghourmanagement.databinding.ItemOwnerStaffTodoManagementBinding
import myapplication.second.workinghourmanagement.databinding.ItemOwnerStoreConversionListBinding
import myapplication.second.workinghourmanagement.dto.ResultGetOwnerStaffTodo
import myapplication.second.workinghourmanagement.dto.ResultGetStore

class OwnerStaffTodoListManagementAdapter(
    private val onClick: (ResultGetOwnerStaffTodo) -> Unit
): ListAdapter<ResultGetOwnerStaffTodo, OwnerStaffTodoListManagementAdapter.StaffTodoListManagementViewHolder>(DiffCallback()) {

    private val staffTodoList = mutableListOf<ResultGetOwnerStaffTodo>()
    private var isRadioButtonVisible = false

    class StaffTodoListManagementViewHolder(
        val binding: ItemOwnerStaffTodoManagementBinding,
        private val onClick: (ResultGetOwnerStaffTodo) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(todoItem: ResultGetOwnerStaffTodo) {

            binding.root.setOnClickListener {
                onClick(todoItem)
            }

            binding.todoItem = todoItem
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OwnerStaffTodoListManagementAdapter.StaffTodoListManagementViewHolder {
        val binding = ItemOwnerStaffTodoManagementBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return OwnerStaffTodoListManagementAdapter.StaffTodoListManagementViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: OwnerStaffTodoListManagementAdapter.StaffTodoListManagementViewHolder, position: Int) {
        val ownerItem = staffTodoList[position]

        holder.bind(ownerItem)
    }

    override fun getItemCount(): Int {
        return staffTodoList.size
    }

    fun setStaffTodoList(store: List<ResultGetOwnerStaffTodo>) {
        staffTodoList.clear()
        staffTodoList.addAll(store)
        notifyDataSetChanged()
    }

    fun add(position: Int, store: ResultGetOwnerStaffTodo) {
        staffTodoList.add(position, store)
        notifyItemInserted(position)
    }

    fun replaceItem(store: ResultGetOwnerStaffTodo) {
        val index = staffTodoList.indexOf(store)
        staffTodoList[index] = store
        notifyItemChanged(index)
    }

    fun toggleRadioButtonVisibility() {
        isRadioButtonVisible = !isRadioButtonVisible
        notifyDataSetChanged()
    }

    private class DiffCallback : DiffUtil.ItemCallback<ResultGetOwnerStaffTodo>(){
        override fun areItemsTheSame(oldItem: ResultGetOwnerStaffTodo, newItem: ResultGetOwnerStaffTodo): Boolean {
            return oldItem.todoId == newItem.todoId
        }

        override fun areContentsTheSame(oldItem: ResultGetOwnerStaffTodo, newItem: ResultGetOwnerStaffTodo): Boolean {
            return oldItem == newItem
        }
    }
}