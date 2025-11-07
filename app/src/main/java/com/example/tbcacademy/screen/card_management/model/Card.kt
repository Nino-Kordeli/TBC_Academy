package com.example.tbcacademy.screen.card_management.model

import androidx.annotation.DrawableRes
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Card(
    val id: Int = 0,
    val cardHolder: String,
    val cardNumber: String,
    @Json(name = "expiry")
    val expirationDate: String,
    val cvv: String,
    val cardType: CardType,
    @Transient
    @DrawableRes
    val backgroundRes: Int = 0
)

enum class CardType {
    @Json(name = "MASTERCARD")
    MASTERCARD,
    @Json(name = "VISA")
    VISA
}