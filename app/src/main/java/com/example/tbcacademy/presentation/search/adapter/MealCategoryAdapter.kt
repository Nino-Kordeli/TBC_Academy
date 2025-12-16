package com.example.tbcacademy.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcacademy.R
import com.example.tbcacademy.databinding.MealCategoryItemBinding
import com.example.tbcacademy.domain.model.FoodCategory

class MealCategoryAdapter(
    private val categories: List<FoodCategory>
) : RecyclerView.Adapter<MealCategoryAdapter.MealCategoryViewHolder>() {

    inner class MealCategoryViewHolder(
        val binding: MealCategoryItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealCategoryViewHolder {
        val binding = MealCategoryItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MealCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealCategoryViewHolder, position: Int) {
        val category = categories[position]

        holder.binding.tvCategoryName.text = category.name.lowercase()
            .replaceFirstChar { it.uppercase() }

        val imageRes = when (category) {
            FoodCategory.BREAKFAST -> R.drawable.breakfast_item
            FoodCategory.LUNCH -> R.drawable.lunch_item
            FoodCategory.DINNER -> R.drawable.dinner_item
            FoodCategory.SNACKS -> R.drawable.snacks_item
            FoodCategory.DESSERT -> R.drawable.dessert_item
            FoodCategory.DRINKS -> R.drawable.drinks_item
        }

        holder.binding.ivCategory.setImageResource(imageRes)
    }

    override fun getItemCount(): Int = categories.size
}
