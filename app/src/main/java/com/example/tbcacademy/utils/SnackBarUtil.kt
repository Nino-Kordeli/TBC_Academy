package com.example.tbcacademy.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

/**
 * Shows a quick message at the bottom of the screen using Snackbar.
 *
 * @param title The message to show.
 * @param duration How long the message stays visible. Default is short.
 */
fun View.showSnackBar(title: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, title, duration).show()
}
