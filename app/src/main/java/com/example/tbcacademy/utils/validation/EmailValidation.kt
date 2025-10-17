package com.example.tbcacademy.utils.validation

/**
 * Checks if the string is a valid email address format.
 *
 * @return `true` if the string matches a valid email pattern, `false` otherwise.
 */
fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
