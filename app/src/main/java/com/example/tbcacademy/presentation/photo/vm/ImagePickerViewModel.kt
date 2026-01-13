package com.example.tbcacademy.presentation.photo.vm

import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.domain.usecase.UploadImageUseCase
import com.example.tbcacademy.presentation.common.BaseViewModel
import com.example.tbcacademy.presentation.photo.contract.ImagePickerEvent
import com.example.tbcacademy.presentation.photo.contract.ImagePickerSideEffect
import com.example.tbcacademy.presentation.photo.contract.ImagePickerUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagePickerViewModel @Inject constructor(
    private val uploadImageUseCase: UploadImageUseCase
) : BaseViewModel<
        ImagePickerUiState,
        ImagePickerEvent,
        ImagePickerSideEffect
        >(
    ImagePickerUiState()
) {

    override fun onEvent(event: ImagePickerEvent) {
        when (event) {
            is ImagePickerEvent.UploadImage ->
                uploadImage(event.bytes)

            is ImagePickerEvent.ImageSelected ->
                updateState { it.copy(selectedImageUri = event.uri) }
        }
    }

    private fun uploadImage(bytes: ByteArray) {
        viewModelScope.launch {
            updateState { it.copy(loading = true) }

            runCatching {
                uploadImageUseCase(bytes)
            }.onSuccess {
                emitSideEffect(
                    ImagePickerSideEffect.ShowToast(
                        "Upload successful"
                    )
                )
            }.onFailure { throwable ->
                emitSideEffect(
                    ImagePickerSideEffect.ShowToast(
                        mapErrorToMessage(throwable)
                    )
                )
            }

            updateState { it.copy(loading = false) }
        }
    }

    private fun mapErrorToMessage(throwable: Throwable): String =
        when {
            throwable.message?.contains("permission", ignoreCase = true) == true ->
                "Permission denied. Check Firebase Storage rules"

            throwable.message?.contains("network", ignoreCase = true) == true ->
                "Network error. Check internet connection"

            else ->
                "Upload failed"
        }
}
