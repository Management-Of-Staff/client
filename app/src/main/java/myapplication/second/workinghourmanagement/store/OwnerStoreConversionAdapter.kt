package myapplication.second.workinghourmanagement.store

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import myapplication.second.workinghourmanagement.databinding.ItemOwnerStoreConversionListBinding
import myapplication.second.workinghourmanagement.dto.store.ResponseGetStore

class OwnerStoreConversionAdapter(
    private val onClick: (ResponseGetStore) -> Unit
): ListAdapter<ResponseGetStore, OwnerStoreConversionAdapter.ConvertStoreViewHolder>(DiffCallback()) {

    private val storeList = mutableListOf<ResponseGetStore>()
    private var isRadioButtonVisible = false

    class ConvertStoreViewHolder(
        val binding: ItemOwnerStoreConversionListBinding,
        private val onClick: (ResponseGetStore) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(storeItem: ResponseGetStore) {
            binding.tvStoreTitle.text = storeItem.storeName
            binding.tvStoreSubtitle.text = storeItem.branchName

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
        val storeItem = currentList[position]

        if (isRadioButtonVisible) {
            holder.binding.rbChoiceConvertStore.visibility = View.VISIBLE
            holder.binding.ivChoiceConvertStore.visibility = View.GONE
        } else {
            holder.binding.rbChoiceConvertStore.visibility = View.GONE
            holder.binding.ivChoiceConvertStore.visibility = View.VISIBLE
        }

        holder.bind(storeItem)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    fun setStoreList(store: List<ResponseGetStore>) {
        storeList.clear()
        storeList.addAll(store)
        notifyDataSetChanged()
    }

    fun add(position: Int, store: ResponseGetStore) {
        storeList.add(position, store)
        notifyItemInserted(position)
    }

    fun replaceItem(store: ResponseGetStore) {
        val index = storeList.indexOf(store)
        storeList[index] = store
        notifyItemChanged(index)
    }

    fun toggleRadioButtonVisibility() {
        isRadioButtonVisible = !isRadioButtonVisible
        notifyDataSetChanged()
    }

    private class DiffCallback : DiffUtil.ItemCallback<ResponseGetStore>(){
        override fun areItemsTheSame(oldItem: ResponseGetStore, newItem: ResponseGetStore): Boolean {
            return oldItem.storeId == newItem.storeId
        }

        override fun areContentsTheSame(oldItem: ResponseGetStore, newItem: ResponseGetStore): Boolean {
            return oldItem == newItem
        }
    }
}