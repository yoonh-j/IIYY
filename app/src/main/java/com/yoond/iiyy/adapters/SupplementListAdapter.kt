package com.yoond.iiyy.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yoond.iiyy.data.Supplement
import com.yoond.iiyy.databinding.ItemHomeBinding

/**
 * Adapter for the [RecyclerView] in [HomeFragment]
 */
class SupplementAdapter(
    val deleteClickListener: OnDeleteClickListener,
    val checkClickListener: OnCheckClickListener
) : ListAdapter<Supplement, RecyclerView.ViewHolder>(SupplementDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        SupplementViewHolder(
            ItemHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val sup = getItem(position)
        (holder as SupplementViewHolder).bind(sup)
    }

    inner class SupplementViewHolder(
        private val binding: ItemHomeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setCheckClickListener {
                val item = binding.supplement
                if (item != null) {
                    item.status = !item.status
                    Log.d("SUPPLEMENT_LIST_ADAPTER", item.toString())
                    checkClickListener.onCheckClick(item)
                }
                binding.itemHomeCheck.isChecked = !binding.itemHomeCheck.isChecked
            }
            binding.setDeleteClickListener {
                val item = binding.supplement
                if (item != null) {
                    deleteClickListener.onDeleteClick(item)
                }
            }
        }

        fun bind(item: Supplement) {
            binding.apply {
                supplement = item
                executePendingBindings()
            }
        }
    }

    interface OnCheckClickListener {
        fun onCheckClick(supplement: Supplement)
    }

    interface OnDeleteClickListener {
        fun onDeleteClick(supplement: Supplement)
    }
}

private class SupplementDiffCallback : DiffUtil.ItemCallback<Supplement>() {
    override fun areItemsTheSame(oldItem: Supplement, newItem: Supplement) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Supplement, newItem: Supplement) =
        oldItem == newItem
}