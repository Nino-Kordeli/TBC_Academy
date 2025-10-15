package com.example.tbcacademy

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val birthday: Long,
    val address: String,
    val email: String,
    val desc: String? = null
) : Parcelable