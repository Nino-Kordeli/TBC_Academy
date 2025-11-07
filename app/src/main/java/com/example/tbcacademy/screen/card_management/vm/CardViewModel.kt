package com.example.tbcacademy.screen.card_management.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.R
import com.example.tbcacademy.screen.card_management.model.Card
import com.example.tbcacademy.screen.card_management.model.CardType
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File

class CardViewModel(application: Application) : AndroidViewModel(application) {
    private val _cards = MutableStateFlow<List<Card>>(emptyList())
    val cards: StateFlow<List<Card>> = _cards

    private val moshi = Moshi.Builder().build()
    private val type = Types.newParameterizedType(List::class.java, Card::class.java)
    private val adapter = moshi.adapter<List<Card>>(type)
    private val file = File(application.filesDir, "cards.json")

    init {
        loadCards()
    }

    private fun loadCards() {
        viewModelScope.launch {
            try {
                val cardsList = if (file.exists()) {
                    file.readText().let { json ->
                        adapter.fromJson(json) ?: getDefaultCards()
                    }
                } else {
                    getDefaultCards().also { saveCards(it) }
                }
                _cards.value = cardsList.map { it.withBackgroundRes() }
            } catch (e: Exception) {
                e.printStackTrace()
                _cards.value = getDefaultCards().map { it.withBackgroundRes() }
            }
        }
    }

    private fun getDefaultCards(): List<Card> {
        val json = """
        [
          {
            "id": 1,
            "cardHolder": "Selena Gomez",
            "cardNumber": "2341 5721 9830 1214",
            "expiry": "12/28",
            "cvv": "123",
            "cardType": "MASTERCARD"
          },
          {
            "id": 2,
            "cardHolder": "Orlando Bloom",
            "cardNumber": "4325 6774 7610 9876",
            "expiry": "05/27",
            "cvv": "621",
            "cardType": "VISA"
          },
          {
            "id": 3,
            "cardHolder": "Alex Shadow",
            "cardNumber": "9403 1530 2049 9876",
            "expiry": "02/28",
            "cvv": "456",
            "cardType": "VISA"
          }
        ]
        """.trimIndent()
        return adapter.fromJson(json) ?: emptyList()
    }

    fun addCard(newCard: Card) {
        val current = _cards.value.toMutableList()
        val maxId = current.maxOfOrNull { it.id } ?: 0
        val cardWithId = newCard.copy(id = maxId + 1).withBackgroundRes()
        current.add(cardWithId)
        _cards.value = current
        saveCards(current)
    }

    fun deleteCard(card: Card) {
        val current = _cards.value.filter { it.id != card.id }
        _cards.value = current
        saveCards(current)
    }

    private fun saveCards(cards: List<Card>) {
        viewModelScope.launch {
            try {
                file.writeText(adapter.toJson(cards))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun Card.withBackgroundRes(): Card {
        return this.copy(
            backgroundRes = when (this.cardType) {
                CardType.MASTERCARD -> R.drawable.mastercard_card
                CardType.VISA -> R.drawable.visa_card
            }
        )
    }
}