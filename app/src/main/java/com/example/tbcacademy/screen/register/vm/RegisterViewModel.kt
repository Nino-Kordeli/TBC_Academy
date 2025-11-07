package com.example.tbcacademy.screen.register.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.screen.register.adapter.BooleanAdapter
import com.example.tbcacademy.screen.register.model.FieldItem
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val _fields = MutableStateFlow<List<FieldItem>>(emptyList())
    val fields: StateFlow<List<FieldItem>> get() = _fields

    fun loadFieldsFromResource(resId: Int) {
        viewModelScope.launch {
            val json = getApplication<Application>().resources.openRawResource(resId)
                .bufferedReader()
                .use { it.readText() }

            val moshi = Moshi.Builder()
                .add(BooleanAdapter())
                .build()
            val type = Types.newParameterizedType(
                List::class.java,
                Types.newParameterizedType(List::class.java, FieldItem::class.java)
            )
            val jsonAdapter = moshi.adapter<List<List<FieldItem>>>(type)
            val outerList = jsonAdapter.fromJson(json) ?: emptyList()
            val fieldList = outerList.flatten()
            _fields.value = fieldList
        }
    }

    fun updateFieldValue(fieldId: Int, newValue: String) {
        _fields.value = _fields.value.map { item ->
            if (item.fieldId == fieldId) item.copy(userValue = newValue) else item
        }
    }

    fun validateFields(): List<String> {
        return _fields.value
            .filter { it.required && it.userValue.isBlank() }
            .map { "${it.hint} is required" }
    }

    fun collectData(): Map<String, String> {
        return _fields.value.associate { it.hint to it.userValue }
    }
}
