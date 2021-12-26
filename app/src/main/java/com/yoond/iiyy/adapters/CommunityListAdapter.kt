package com.yoond.iiyy.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yoond.iiyy.data.Community
import com.yoond.iiyy.databinding.ItemCommunityBinding

class CommunityListAdapter()
    : ListAdapter<Community, RecyclerView.ViewHolder>(CommunityDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        CommunityViewHolder(
            ItemCommunityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val com = getItem(position)
        (holder as CommunityViewHolder).bind(com)
    }

    class CommunityViewHolder(
        private val binding: ItemCommunityBinding
    ): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                Log.d("COMMUNITY_LIST_ADAPTER", binding.itemComTitle.text.toString())
            }
        }

        fun bind(item: Community) {
            binding.apply {
                community = item
            }
        }
    }
}

private class CommunityDiffCallback(): DiffUtil.ItemCallback<Community>() {
    override fun areItemsTheSame(oldItem: Community, newItem: Community) =
        oldItem.key == newItem.key

    override fun areContentsTheSame(oldItem: Community, newItem: Community) =
        oldItem == newItem

}