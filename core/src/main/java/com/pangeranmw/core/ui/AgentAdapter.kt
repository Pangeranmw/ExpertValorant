package com.pangeranmw.core.ui

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pangeranmw.core.databinding.ItemCardAgentBinding
import com.pangeranmw.core.domain.model.Agent

class AgentAdapter(var onItemClick:(Agent) -> Unit): ListAdapter<Agent, AgentAdapter.ItemViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemCardAgentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ItemViewHolder(private val binding: ItemCardAgentBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(agent: Agent) {
            binding.apply {
                tvAgentName.text = agent.name
                tvAgentRole.text = agent.role.displayName
                Glide.with(itemView.context)
                    .load(agent.fullPortrait)
                    .into(imgAgent)

                Glide.with(itemView.context)
                    .load(agent.background)
                    .into(imgBg)

                // Define colors using hex codes
                val colors = intArrayOf(
                    Color.parseColor("#${agent.backgroundGradientColors[0]}"),
                    Color.parseColor("#${agent.backgroundGradientColors[1]}"),
                    Color.parseColor("#${agent.backgroundGradientColors[2]}"),
                    Color.parseColor("#${agent.backgroundGradientColors[3]}")
                )

                val gradientDrawable = GradientDrawable(
                    GradientDrawable.Orientation.BOTTOM_TOP, // Change the orientation as needed
                    colors
                )

                imgBg.background = gradientDrawable

                agent.abilities.findLast { it.slot == "Ability1" }?.let{ ability->
                    Glide.with(itemView.context)
                        .load(ability.displayIcon)
                        .into(ability1)
                }
                agent.abilities.findLast { it.slot == "Ability2" }?.let{ ability->
                    Glide.with(itemView.context)
                        .load(ability.displayIcon)
                        .into(ability2)
                }
                agent.abilities.findLast { it.slot == "Grenade" }?.let{ ability->
                    Glide.with(itemView.context)
                        .load(ability.displayIcon)
                        .into(ability3)
                }
                agent.abilities.findLast { it.slot == "Ultimate" }?.let{ ability->
                    Glide.with(itemView.context)
                        .load(ability.displayIcon)
                        .into(ability4)
                }
                itemView.setOnClickListener {
                    onItemClick(agent)
                }
            }
        }
    }
    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Agent>() {

            override fun areItemsTheSame(oldItem: Agent, newItem: Agent): Boolean {
                Log.d("AgentAdapter", "areItemsTheSame: ${oldItem.agentId == newItem.agentId}")
                return oldItem.agentId == newItem.agentId
            }

            override fun areContentsTheSame(oldItem: Agent, newItem: Agent): Boolean {
                Log.d("AgentAdapter", "areContentsTheSame: ${oldItem == newItem}")
                return oldItem == newItem
            }
        }
    }
}