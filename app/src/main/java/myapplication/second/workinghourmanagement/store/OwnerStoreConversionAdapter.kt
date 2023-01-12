package myapplication.second.workinghourmanagement.store

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import myapplication.second.workinghourmanagement.databinding.ItemOwnerStoreConversionListBinding
import myapplication.second.workinghourmanagement.dto.ResultGetStore

class OwnerStoreConversionAdapter(
    private val onClick: (ResultGetStore) -> Unit
): ListAdapter<ResultGetStore, OwnerStoreConversionAdapter.ConvertStoreViewHolder>(DiffCallback()) {

    private val storeList = mutableListOf<ResultGetStore>()
    private var isRadioButtonVisible = false

    class ConvertStoreViewHolder(
        val binding: ItemOwnerStoreConversionListBinding,
        private val onClick: (ResultGetStore) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(storeItem: ResultGetStore) {

            binding.root.setOnClickListener {
                onClick(storeItem)
            }

            binding.storeItem = storeItem
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConvertStoreViewHolder {
        val binding = ItemOwnerStoreConversionListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ConvertStoreViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: ConvertStoreViewHolder, position: Int) {
        val storeItem = storeList[position]

        if (isRadioButtonVisible) {
            holder.binding.rbChoiceConvertStore.visibility = View.VISIBLE
        } else {
            holder.binding.rbChoiceConvertStore.visibility = View.GONE
        }

        holder.bind(storeItem)
    }

    override fun getItemCount(): Int {
        return storeList.size
    }

    fun setStoreList(store: List<ResultGetStore>) {
        storeList.clear()
        storeList.addAll(store)
        notifyDataSetChanged()
    }

    fun add(position: Int, store: ResultGetStore) {
        storeList.add(position, store)
        notifyItemInserted(position)
    }

    fun replaceItem(store: ResultGetStore) {
        val index = storeList.indexOf(store)
        storeList[index] = store
        notifyItemChanged(index)
    }

    fun toggleRadioButtonVisibility() {
        isRadioButtonVisible = !isRadioButtonVisible
        notifyDataSetChanged()
    }
}

private class DiffCallback : DiffUtil.ItemCallback<ResultGetStore>(){
    override fun areItemsTheSame(oldItem: ResultGetStore, newItem: ResultGetStore): Boolean {
        return oldItem.storeId == newItem.storeId
    }

    override fun areContentsTheSame(oldItem: ResultGetStore, newItem: ResultGetStore): Boolean {
        return oldItem == newItem
    }
}