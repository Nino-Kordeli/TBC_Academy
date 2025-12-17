package com.example.tbcacademy.presentation.recipes_by_ingredient.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tbcacademy.databinding.RecipeItemBinding
import com.example.tbcacademy.presentation.model.RecipeUi

class RecipesByIngredientAdapter(
    private val onClick: (Int) -> Unit
) : ListAdapter<RecipeUi, RecipesByIngredientAdapter.ViewHolder>(RecipeDiffCallback()) {

    inner class ViewHolder(private val binding: RecipeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: RecipeUi) {
            binding.recipeName.text = recipe.name
            Glide.with(binding.ivRecipeImage.context)
                .load(recipe.imageUrl)
                .into(binding.ivRecipeImage)

            binding.root.setOnClickListener { onClick(recipe.id) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(RecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    class RecipeDiffCallback : DiffUtil.ItemCallback<RecipeUi>() {
        override fun areItemsTheSame(oldItem: RecipeUi, newItem: RecipeUi) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: RecipeUi, newItem: RecipeUi) = oldItem == newItem
    }
}
