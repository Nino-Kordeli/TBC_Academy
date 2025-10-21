package com.example.tbcacademy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcacademy.databinding.ItemUserBinding
import com.example.tbcacademy.model.User

class UserAdapter(
    private val onLongClick: (User) -> Unit
) : ListAdapter<User, UserAdapter.UserViewHolder>(UserDiffCallback()) {

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) = with(binding) {
            tvUserName.text =
                itemView.context.getString(R.string.full_name, user.firstName, user.lastName)
            tvUserEmail.text = user.email
            tvUserAge.text = itemView.context.getString(R.string.age_text, user.age)

            root.setOnLongClickListener {
                onLongClick(user)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding =
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class UserDiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
        oldItem.email == newItem.email

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
        oldItem == newItem
}
