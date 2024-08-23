package com.pangeranmw.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pangeranmw.core.databinding.ItemCardAgentRecommendationBinding
import com.pangeranmw.core.domain.model.Agent

class SimillarAgentRoleAdapter(var onItemClick:(Agent) -> Unit): ListAdapter<Agent, SimillarAgentRoleAdapter.ItemViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemCardAgentRecommendationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ItemViewHolder(private val binding: ItemCardAgentRecommendationBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(agent: Agent) {
            binding.apply {
                tvAgentName.text = agent.name
                Glide.with(itemView.context)
                    .load(agent.fullPortrait)
                    .into(imgAgent)

                Glide.with(itemView.context)
                    .load(agent.background)
                    .into(imgBg)

                itemView.setOnClickListener {
                    onItemClick(agent)
                }
            }
        }
    }
    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Agent>() {

            override fun areItemsTheSame(oldItem: Agent, newItem: Agent): Boolean {
                return oldItem.agentId == newItem.agentId
            }

            override fun areContentsTheSame(oldItem: Agent, newItem: Agent): Boolean {
                return oldItem == newItem
            }
        }
    }
}