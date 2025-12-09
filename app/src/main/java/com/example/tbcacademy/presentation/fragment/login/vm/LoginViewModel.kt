package com.example.tbcacademy.presentation.fragment.login.vm

import com.example.tbcacademy.common.BaseViewModel
import com.example.tbcacademy.domain.usecase.LoginUseCase
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
)  {

}