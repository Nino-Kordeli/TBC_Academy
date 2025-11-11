package com.example.tbcacademy.screen.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tbcacademy.R
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.databinding.FragmentLoginBinding
import com.example.tbcacademy.screen.login.vm.LoginViewModel


class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {
    override val viewModel: LoginViewModel
        get() = TODO("Not yet implemented")

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding {
        TODO("Not yet implemented")
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

}