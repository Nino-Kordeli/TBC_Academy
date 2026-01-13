package com.example.tbcacademy.presentation.photo.contract

import android.net.Uri

data class ImagePickerUiState(
    val loading: Boolean = false,
    val selectedImageUri: Uri? = null,
)

sealed class ImagePickerEvent {
    data class UploadImage(val bytes: ByteArray) : ImagePickerEvent() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as UploadImage

            if (!bytes.contentEquals(other.bytes)) return false

            return true
        }

        override fun hashCode(): Int {
            return bytes.contentHashCode()
        }
    }

    data class ImageSelected(val uri: Uri) : ImagePickerEvent()
}

sealed class ImagePickerSideEffect {
    data class ShowToast(val message: String) : ImagePickerSideEffect()
}
