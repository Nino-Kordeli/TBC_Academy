package com.example.tbcacademy.presentation.screens.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tbcacademy.databinding.ItemUserBinding
import com.example.tbcacademy.data.dto.UserDto

class UsersPagingAdapter :
    PagingDataAdapter<UserDto, UsersPagingAdapter.UserViewHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(u: UserDto) {
            binding.tvUserName.text = "${u.firstname} ${u.lastname}"
            binding.tvUserEmail.text = u.email
            Glide.with(binding.root).load(u.avatar).into(binding.ivUserAvatar)
        }
    }

    class UserDiffCallback : DiffUtil.ItemCallback<UserDto>() {
        override fun areItemsTheSame(a: UserDto, b: UserDto) = a.id == b.id
        override fun areContentsTheSame(a: UserDto, b: UserDto) = a == b
    }
}