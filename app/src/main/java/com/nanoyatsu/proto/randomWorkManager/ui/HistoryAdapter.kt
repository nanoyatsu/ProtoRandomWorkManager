package com.nanoyatsu.proto.randomWorkManager.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nanoyatsu.proto.randomWorkManager.data.database.entity.History
import com.nanoyatsu.proto.randomWorkManager.databinding.ItemHistoryBinding

class HistoryAdapter : PagedListAdapter<History, HistoryAdapter.ViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.also { holder.bind(it) }
    }

    class ViewHolder(val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder =
                ViewHolder(ItemHistoryBinding.inflate(LayoutInflater.from(parent.context)))
        }

        fun bind(history: History) {
            binding.text.text = "${history.id}\n ${history.timestamp}\n ${history.parent}"
        }
    }

    companion object {
        class DiffCallback : DiffUtil.ItemCallback<History>() {
            override fun areItemsTheSame(oldItem: History, newItem: History): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: History, newItem: History): Boolean =
                oldItem == newItem
        }
    }
}