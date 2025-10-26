package com.example.tbcacademy.screen.address_add.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    val id: Int,
    val title: String,
    val description: String
) : Parcelable
