package com.example.tbcacademy.domain.usecase

import javax.inject.Inject

class CheckPasscodeUseCase @Inject constructor() {
    private val correctPassword = "0934"

    fun execute(input: String): Boolean {
        return input == correctPassword
    }
}