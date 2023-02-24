package myapplication.second.workinghourmanagement.store.notice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import myapplication.second.workinghourmanagement.databinding.ItemOwnerNoticeBinding
import myapplication.second.workinghourmanagement.dto.notice.ResponseGetNotice

class OwnerNoticeListAdapter(
    private val onClick: (ResponseGetNotice) -> Unit
): ListAdapter<ResponseGetNotice, OwnerNoticeListAdapter.OwnerNoticeListViewHolder>(DiffCallback()) {

    private val noticeList = mutableListOf<ResponseGetNotice>()

    class OwnerNoticeListViewHolder(
        val binding: ItemOwnerNoticeBinding,
        private val onClick: (ResponseGetNotice) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(noticeItem: ResponseGetNotice) {

            binding.root.setOnClickListener {
                onClick(noticeItem)
            }

            binding.noticeItem = noticeItem
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OwnerNoticeListViewHolder {
        val binding = ItemOwnerNoticeBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return OwnerNoticeListViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: OwnerNoticeListViewHolder, position: Int) {
        val noticeItem = noticeList[position]

        holder.bind(noticeItem)
    }

    override fun getItemCount(): Int {
        return noticeList.size
    }

    fun setNoticeList(noticeList: List<ResponseGetNotice>) {
//        noticeList.clear()
//        noticeList.addAll(noticeList)
        notifyDataSetChanged()
    }

    fun add(position: Int, notice: ResponseGetNotice) {
        noticeList.add(position, notice)
        notifyItemInserted(position)
    }

    fun replaceItem(notice: ResponseGetNotice) {
        val index = noticeList.indexOf(notice)
        noticeList[index] = notice
        notifyItemChanged(index)
    }

    private class DiffCallback : DiffUtil.ItemCallback<ResponseGetNotice>(){
        override fun areItemsTheSame(oldItem: ResponseGetNotice, newItem: ResponseGetNotice): Boolean {
            return oldItem.noticeId == newItem.noticeId
        }

        override fun areContentsTheSame(oldItem: ResponseGetNotice, newItem: ResponseGetNotice): Boolean {
            return oldItem == newItem
        }
    }
}