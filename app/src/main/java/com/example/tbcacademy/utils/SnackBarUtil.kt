package com.example.tbcacademy.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(title: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, title, duration).show()
}