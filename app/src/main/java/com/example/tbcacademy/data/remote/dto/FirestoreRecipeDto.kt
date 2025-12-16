package com.example.tbcacademy.data.remote.dto

import com.google.firebase.firestore.PropertyName

data class FirestoreRecipeDto(
    @PropertyName("id")
    val id: Int = 0,

    @PropertyName("name")
    val name: String = "",

    @PropertyName("imageUrl")
    val imageUrl: String = ""
) {
    constructor() : this(0, "", "")
}