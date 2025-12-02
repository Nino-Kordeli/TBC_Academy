package com.example.tbcacademy.presentation.lock_screen.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.domain.usecase.CheckPasscodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LockScreenViewModel @Inject constructor(
    private val checkPasscodeUseCase: CheckPasscodeUseCase
) : ViewModel() {

    private val _input = mutableListOf<String>()
    val input: List<String> get() = _input

    private val _status = MutableStateFlow("")
    val status: StateFlow<String> = _status

    private val _dots = MutableStateFlow(listOf(false, false, false, false))
    val dots: StateFlow<List<Boolean>> = _dots

    fun addDigit(digit: String) {
        if (_input.size < 4) {
            _input.add(digit)
            updateDots()
            if (input.size == 4) checkPassword()
        }
    }

    fun removeDigit() {
        if (_input.isNotEmpty()) {
            _input.removeAt(_input.lastIndex)
            updateDots()
        }
    }

    fun updateDots() {
        _dots.value = List(4) { index -> index < _input.size }
    }

    private fun checkPassword() {
        val inputStr = _input.joinToString("")
        viewModelScope.launch {
            if (checkPasscodeUseCase.execute(inputStr)) {
                _status.value = "success"
            } else {
                _status.value = "incorrect"
            }
            _input.clear()
            updateDots()
        }
    }

    fun resetStatus(){
        _status.value = ""
    }
}