package com.example.tbcacademy

data class Cell(
    val row: Int,
    val column: Int,
    var value: String = "",
    var isEnabled: Boolean = true
)

