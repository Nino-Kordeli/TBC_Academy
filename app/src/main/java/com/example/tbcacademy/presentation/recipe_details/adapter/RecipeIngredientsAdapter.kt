package com.example.tbcacademy.presentation.recipe_details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tbcacademy.databinding.IngredientDetailItemBinding
import com.example.tbcacademy.presentation.model.IngredientUi
import com.example.tbcacademy.presentation.model.RecipeIngredientUi

class RecipeIngredientsAdapter :
    ListAdapter<IngredientUi, RecipeIngredientsAdapter.IngredientViewHolder>(
        IngredientDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val binding = IngredientDetailItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return IngredientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class IngredientViewHolder(
        private val binding: IngredientDetailItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(ingredient: IngredientUi) {
            binding.tvIngredientName.text = ingredient.name

            Glide.with(binding.ivIngredient.context)
                .load(ingredient.imageUrl)
                .into(binding.ivIngredient)
        }
    }

    class IngredientDiffCallback : DiffUtil.ItemCallback<IngredientUi>() {
        override fun areItemsTheSame(
            oldItem: IngredientUi,
            newItem: IngredientUi,
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: IngredientUi,
            newItem: IngredientUi,
        ) = oldItem == newItem
    }
}