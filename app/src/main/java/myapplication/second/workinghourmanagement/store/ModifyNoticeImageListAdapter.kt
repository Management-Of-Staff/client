package myapplication.second.workinghourmanagement.store

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import myapplication.second.workinghourmanagement.databinding.ItemNoticeImageBinding

class ModifyNoticeImageListAdapter(
    private val onClick: (String) -> Unit,
    private val onCancelClick: (Int) -> Unit
) : ListAdapter<String, ModifyNoticeImageListAdapter.ModifyNoticeImageViewHolder>(DiffCallback()) {
    private val noticeImageList = mutableListOf<String>()

    class ModifyNoticeImageViewHolder(
        private val binding: ItemNoticeImageBinding,
        private val onClick: (String) -> Unit,
        private val onCancelClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(image: String) {
            binding.root.setOnClickListener {
                onClick(image)
            }

            binding.ivNoticeImageCancel.setOnClickListener {
                onCancelClick(absoluteAdapterPosition)
            }

            binding.ivNoticeImage.setImageURI(image.toUri())
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModifyNoticeImageViewHolder {
        val binding = ItemNoticeImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ModifyNoticeImageViewHolder(binding, onClick, onCancelClick)
    }

    override fun onBindViewHolder(holder: ModifyNoticeImageViewHolder, position: Int) {
        val image = noticeImageList[position]
        holder.bind(image)
    }


    private class DiffCallback : DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}