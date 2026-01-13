package com.example.tbcacademy.data.datasource

import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class FirebaseImageDataSource @Inject constructor(
    private val storage: FirebaseStorage
) {

    suspend fun uploadImage(bytes: ByteArray): String {
        val imageId = UUID.randomUUID().toString()
        val ref = storage.reference.child("images/$imageId.jpg")

        ref.putBytes(bytes).await()
        return ref.downloadUrl.await().toString()
    }
}
