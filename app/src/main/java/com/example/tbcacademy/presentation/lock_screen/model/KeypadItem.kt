package com.example.tbcacademy.presentation.lock_screen.model

sealed class KeypadItem {
    data class Number(val number: String) : KeypadItem()
    object Delete : KeypadItem()
    object Fingerprint : KeypadItem()
}