package com.example.tbcacademy.screen.register.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class BooleanAdapter {
    @FromJson
    fun fromJson(value: Any?): Boolean {
        return when(value) {
            is Boolean -> value
            is String -> value.equals("true", ignoreCase = true)
            else -> false
        }
    }

    @ToJson
    fun toJson(value: Boolean): Boolean = value
}