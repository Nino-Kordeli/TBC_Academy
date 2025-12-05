package com.example.tbcacademy.presentation.users.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.tbcacademy.R
import com.example.tbcacademy.databinding.ItemUserBinding
import com.example.tbcacademy.domain.model.User

class UsersAdapter : ListAdapter<User, UsersAdapter.UserViewHolder>(UserDiffCallback()) {

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) = with(binding) {
            tvUserName.text = user.fullName ?: itemView.context.getString(R.string.unknown)
            tvActivationStatus.text =
                user.lastActiveDescription ?: itemView.context.getString(R.string.unknown)

            ivUserProfile.load(user.profileImageUrl) {
                placeholder(R.drawable.ic_launcher_foreground)
                error(R.drawable.ic_launcher_foreground)
            }

            val color = when {
                (user.activationStatus ?: 0) <= 0 -> Color.GRAY
                user.activationStatus == 1 -> Color.GREEN
                user.activationStatus == 2 -> Color.YELLOW
                user.activationStatus in 3..22 -> Color.BLUE
                else -> Color.RED
            }
            viewStatusIndicator.setBackgroundColor(color)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class UserDiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: User, newItem: User) = oldItem == newItem
}
