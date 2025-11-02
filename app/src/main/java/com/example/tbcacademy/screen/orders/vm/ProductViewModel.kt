package com.example.tbcacademy.screen.orders.vm

import androidx.lifecycle.ViewModel
import com.example.tbcacademy.R
import com.example.tbcacademy.screen.my_orders.model.ItemColor
import com.example.tbcacademy.screen.my_orders.model.Product
import com.example.tbcacademy.screen.my_orders.model.Status
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProductViewModel : ViewModel() {

    private val _products = MutableStateFlow(
        listOf(
            Product(1, "Modern Wingback", ItemColor.BLACK, 5, "$255", Status.ACTIVE, R.mipmap.chair),
            Product(2, "Wooden Chair", ItemColor.WHITE, 2, "$380", Status.ACTIVE,R.mipmap.wooden_chair),
            Product(3, "Wardrobe", ItemColor.WHITE, 4, "$120", Status.COMPLETED,R.mipmap.wardrobe),
            Product(4, "Coffee Table", ItemColor.GREEN, 1, "$290", Status.ACTIVE,R.mipmap.table),
            Product(5, "Sofa", ItemColor.BLUE, 3, "$695", Status.COMPLETED,R.mipmap.sofa),
            Product(6, "Lamp", ItemColor.WHITE, 2, "$340", Status.ACTIVE,R.mipmap.lamp)
        )
    )
    val products = _products.asStateFlow()

    fun updateProductStatus(productId: Int, newStatus: Status) {
        _products.update { currentList ->
            currentList.map { product ->
                if (product.id == productId) {
                    product.copy(status = newStatus)
                } else {
                    product
                }
            }
        }
    }
}