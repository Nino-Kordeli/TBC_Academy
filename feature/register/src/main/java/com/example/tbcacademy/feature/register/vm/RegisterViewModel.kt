package com.example.tbcacademy.feature.register.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.core.domain.common.Resource
import com.example.tbcacademy.core.domain.model.Field
import com.example.tbcacademy.core.domain.usecase.GetRegisterFieldsUseCase
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
            getFieldsUseCase().collect { resource ->
                _state.value = resource
            }
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