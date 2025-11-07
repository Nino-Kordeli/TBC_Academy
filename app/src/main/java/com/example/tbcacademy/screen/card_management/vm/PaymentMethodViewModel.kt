package com.example.tbcacademy.screen.card_management.vm

import androidx.lifecycle.ViewModel
import com.example.tbcacademy.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PaymentMethodViewModel : ViewModel() {
    private val _cards = MutableStateFlow<List<Int>>(emptyList())
    val cards = _cards.asStateFlow()

    init {
        _cards.value = listOf(
            R.drawable.paypal_icon,
            R.drawable.visa_icon,
            R.drawable.mastercard_icon,
            R.drawable.alipay_icon,
            R.drawable.amex_icon,
        )
    }
}