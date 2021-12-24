package com.yoond.iiyy.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yoond.iiyy.data.Supplement
import com.yoond.iiyy.databinding.ItemCalDetailBinding

class CalendarDetailAdapter()
    : ListAdapter<Supplement, RecyclerView.ViewHolder>(CalendarDetailDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CalendarDetailViewHolder(
            ItemCalDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val sup = getItem(position)
        (holder as CalendarDetailViewHolder).bind(sup)
    }

    class CalendarDetailViewHolder(
        private val binding: ItemCalDetailBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Supplement) {
            binding.apply {
                supplement = item
                executePendingBindings()
            }
        }
    }
}

private class CalendarDetailDiffCallback : DiffUtil.ItemCallback<Supplement>() {
    override fun areItemsTheSame(oldItem: Supplement, newItem: Supplement): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Supplement, newItem: Supplement): Boolean {
        return oldItem == newItem
    }
}