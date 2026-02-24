package com.example.data.response_handler.auth

import com.example.common.resource.Resource
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HandleRegisterResponse @Inject constructor() {
    fun apiCall(call: suspend () -> AuthResult): Flow<Resource<String>> = flow {
        emit(Resource.Loading(loading = true))
        try {
            val authResult = call()
            val user = authResult.user ?: throw FirebaseAuthInvalidUserException(
                "ERROR_NO_SIGNED_IN_USER",
                "Registration succeeded but no user was returned."
            )
            emit(Resource.Success(data = user.uid))
        } catch (e: FirebaseAuthWeakPasswordException) {
            emit(Resource.Error("Password is too weak. Please choose a stronger password."))
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            emit(Resource.Error("The email address is badly formatted."))
        } catch (e: FirebaseAuthUserCollisionException) {
            emit(Resource.Error("An account already exists with this email address."))
        } catch (e: FirebaseTooManyRequestsException) {
            emit(Resource.Error("Too many requests. Try again later."))
        } catch (e: FirebaseAuthException) {
            emit(Resource.Error("Registration error: ${e.localizedMessage}"))
        } catch (e: Exception) {
            emit(Resource.Error("Registration failed: ${e.message ?: "An unknown error occurred."}"))
        } finally {
            emit(Resource.Loading(loading = false))
        }
    }
}