package com.example.tbcacademy.presentation.common.extensions

import android.view.View

/**
 * Makes the view visible.
 */
fun View.show() { this.visibility = View.VISIBLE }

/**
 * Hides the view (removes it from the layout).
 */
fun View.hide() { this.visibility = View.GONE }
