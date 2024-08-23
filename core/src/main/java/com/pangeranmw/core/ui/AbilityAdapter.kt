package com.pangeranmw.core.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pangeranmw.core.databinding.ItemCardAbilityBinding
import com.pangeranmw.core.domain.model.Ability

class AbilityAdapter(var onItemClick:(Ability) -> Unit): ListAdapter<Ability, AbilityAdapter.ItemViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemCardAbilityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ItemViewHolder(private val binding: ItemCardAbilityBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(ability: Ability) {
            binding.apply {
                tvSkill.text = when(ability.slot){
                    "Ability1" -> "Q"
                    "Ability2" -> "E"
                    "Grenade" -> "C"
                    "Ultimate" -> "X"
                    else -> "-"
                }
                tvAbilityName.text = ability.slot
                Log.d("AbilityAdapter", "icon up: $ability.displayIcon")
                ability.displayIcon?.let{ icon->
                    Log.d("AbilityAdapter", "icon: $icon")
                    Glide.with(itemView.context)
                        .load(icon)
                        .into(imgAbility)
                }
                itemView.setOnClickListener {
                    onItemClick(ability)
                }
            }
        }
    }
    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Ability>() {

            override fun areItemsTheSame(oldItem: Ability, newItem: Ability): Boolean {
                return oldItem.description == newItem.description
            }

            override fun areContentsTheSame(oldItem: Ability, newItem: Ability): Boolean {
                return oldItem == newItem
            }
        }
    }
}