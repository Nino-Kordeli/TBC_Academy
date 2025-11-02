package com.example.tbcacademy.screen.my_orders.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcacademy.R
import com.example.tbcacademy.databinding.FurnitureItemBinding
import com.example.tbcacademy.screen.my_orders.model.Product
import com.example.tbcacademy.screen.my_orders.model.Status

class ProductAdapter(
    private val onReviewClicked: (Product) -> Unit = {},
    private val hideButtonInDrawer: Boolean = false
) : ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {

    inner class ProductViewHolder(val binding: FurnitureItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            FurnitureItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        with(holder.binding) {
            tvItemTitle.text = product.title
            tvPrice.text = product.price
            tvQuantity.text = product.quantity.toString()
            tvColorText.text = product.color.displayName
            ivColor.setBackgroundResource(product.color.drawableRes)
            ivCardItemBackground.setBackgroundResource(product.imageRes)

            btnReview.visibility = if (hideButtonInDrawer) View.GONE else View.VISIBLE
            btnReview.setOnClickListener(null)

            if (!hideButtonInDrawer) {
                when (product.status) {
                    Status.ACTIVE -> {
                        tvStatus.text = holder.itemView.context.getString(R.string.active)
                        btnReview.text =
                            holder.itemView.context.getString(R.string.leave_a_review)
                        btnReview.isEnabled = true
                        btnReview.alpha = 1f
                        btnReview.setOnClickListener { onReviewClicked(product) }
                    }

                    Status.COMPLETED -> {
                        tvStatus.text = holder.itemView.context.getString(R.string.completed)
                        btnReview.text = holder.itemView.context.getString(R.string.buy_again)
                        btnReview.isEnabled = false
                        btnReview.alpha = 0.5f
                        btnReview.setOnClickListener(null)
                    }
                }
            }
        }
    }
}

class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Product, newItem: Product) =
        oldItem == newItem
}
