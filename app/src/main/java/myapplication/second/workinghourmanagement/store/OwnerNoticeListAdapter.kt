package myapplication.second.workinghourmanagement.store

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import myapplication.second.workinghourmanagement.databinding.ItemOwnerNoticeBinding
import myapplication.second.workinghourmanagement.dto.ResultGetNotice

class OwnerNoticeListAdapter(
    private val onClick: (ResultGetNotice) -> Unit
): ListAdapter<ResultGetNotice, OwnerNoticeListAdapter.OwnerNoticeListViewHolder>(DiffCallback()) {

    private val noticeList = mutableListOf<ResultGetNotice>()

    class OwnerNoticeListViewHolder(
        val binding: ItemOwnerNoticeBinding,
        private val onClick: (ResultGetNotice) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(noticeItem: ResultGetNotice) {

            binding.root.setOnClickListener {
                onClick(noticeItem)
            }

            binding.noticeItem = noticeItem
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OwnerNoticeListAdapter.OwnerNoticeListViewHolder {
        val binding = ItemOwnerNoticeBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return OwnerNoticeListAdapter.OwnerNoticeListViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: OwnerNoticeListAdapter.OwnerNoticeListViewHolder, position: Int) {
        val noticeItem = noticeList[position]

        holder.bind(noticeItem)
    }

    override fun getItemCount(): Int {
        return noticeList.size
    }

    fun setNoticeList(noticeList: List<ResultGetNotice>) {
//        noticeList.clear()
//        noticeList.addAll(noticeList)
        notifyDataSetChanged()
    }

    fun add(position: Int, notice: ResultGetNotice) {
        noticeList.add(position, notice)
        notifyItemInserted(position)
    }

    fun replaceItem(notice: ResultGetNotice) {
        val index = noticeList.indexOf(notice)
        noticeList[index] = notice
        notifyItemChanged(index)
    }

    private class DiffCallback : DiffUtil.ItemCallback<ResultGetNotice>(){
        override fun areItemsTheSame(oldItem: ResultGetNotice, newItem: ResultGetNotice): Boolean {
            return oldItem.noticeId == newItem.noticeId
        }

        override fun areContentsTheSame(oldItem: ResultGetNotice, newItem: ResultGetNotice): Boolean {
            return oldItem == newItem
        }
    }
}