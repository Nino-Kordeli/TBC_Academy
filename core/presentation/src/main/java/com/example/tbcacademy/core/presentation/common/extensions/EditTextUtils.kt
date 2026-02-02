package com.example.tbcacademy.core.presentation.common.extensions

import android.widget.EditText

/**
 * Gets the text from EditText as a String.
 */
fun EditText.textValue() = text.toString()

/**
 * Gets the text from EditText as a trimmed String.
 *
 * Trimming removes spaces at the start and end.
 */
fun EditText.trimmedTextValue() = text.toString().trim()
