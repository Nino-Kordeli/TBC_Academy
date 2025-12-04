package com.example.tbcacademy.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
class UserEntity(
    @PrimaryKey val id: Int,
    val fullName: String?,
    val email: String?,
    val activationStatus: Int?,
    val lastActiveDescription: String?,
    val lastActiveEpoch: Long?,
    val profileImageUrl: String?
)