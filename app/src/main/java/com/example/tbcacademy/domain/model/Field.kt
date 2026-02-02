package com.example.tbcacademy.domain.model

data class Field(
    val id: Int,
    val hint: String,
    val type: String,
    val keyboard: String?,
    val required: Boolean,
    val isActive: Boolean,
    val icon: String,
    val value: String = ""
)