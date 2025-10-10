package com.example.tbcacademy.utils

import android.widget.EditText

fun EditText.textValue() = this.text.toString()

fun EditText.trimmedTextValue() = this.text.toString().trim()



