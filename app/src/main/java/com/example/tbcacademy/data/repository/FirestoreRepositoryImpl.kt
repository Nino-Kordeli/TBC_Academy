package com.example.tbcacademy.data.repository

import com.example.tbcacademy.data.common.HandleFirebaseResponse
import com.example.tbcacademy.data.common.Resource
import com.example.tbcacademy.data.mapper.firestoreToDomain
import com.example.tbcacademy.data.mapper.toFirestoreDto
import com.example.tbcacademy.data.remote.dto.FirestoreRecipeDto
import com.example.tbcacademy.domain.model.Recipe
import com.example.tbcacademy.domain.repository.FirestoreRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val handleResponse: HandleFirebaseResponse,
) : FirestoreRepository {

    override suspend fun saveFavourite(request: Recipe) {
        val uid = auth.currentUser?.uid ?: error("User not logged in")
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(uid)
            .collection("favorites")
            .document(request.id.toString())
            .set(request)
    }


    override fun getFavourites(): Flow<Resource<List<Recipe>>> {
        return handleResponse.authCall {
            val uid = auth.currentUser?.uid ?: error("User not logged in")

            val snapshot = FirebaseFirestore.getInstance()
                .collection("users")
                .document(uid)
                .collection("favorites")
                .get()
                .await()

            snapshot.documents.mapNotNull { document ->
                document.toObject(FirestoreRecipeDto::class.java)?.firestoreToDomain()
            }
        }
    }

    override suspend fun removeFavourite(recipeId: Int) {
        val uid = auth.currentUser?.uid ?: error("User not logged in")
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(uid)
            .collection("favorites")
            .document(recipeId.toString())
            .delete()
    }
}