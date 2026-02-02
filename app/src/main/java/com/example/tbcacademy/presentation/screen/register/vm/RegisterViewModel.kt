package com.example.tbcacademy.presentation.screen.register.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.domain.model.Field
import com.example.tbcacademy.domain.usecase.GetRegisterFieldsUseCase
import com.example.tbcacademy.presentation.common.Resource
import com.example.tbcacademy.presentation.common.safeApiCall
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val getFieldsUseCase: GetRegisterFieldsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<Resource<List<Field>>>(Resource.Loader(true))
    val state: StateFlow<Resource<List<Field>>> = _state

    init {
        loadFields()
    }

    fun loadFields() {
        viewModelScope.launch {
            _state.value = Resource.Loader(true)

            val result = safeApiCall {
                getFieldsUseCase()
            }
            _state.value = result
        }
    }

    fun updateValue(id: Int, value: String) {
        val current = (_state.value as? Resource.Success)?.data ?: return

        val updated = current.map {
            if (it.id == id) it.copy(value = value) else it
        }

        _state.value = Resource.Success(updated)
    }
}