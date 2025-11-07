package com.example.tbcacademy.screen.register.model

enum class InputFieldType(val keyboardType: Int) {
    TEXT(android.text.InputType.TYPE_CLASS_TEXT),
    NUMBER(android.text.InputType.TYPE_CLASS_NUMBER);

    companion object {
        fun fromString(value: String?): InputFieldType {
            return when (value?.lowercase()) {
                "number" -> NUMBER
                else -> TEXT
            }
        }
    }
}
