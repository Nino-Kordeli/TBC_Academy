package com.example.tbcacademy.screen.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.screen.model.DeliveryItem
import com.example.tbcacademy.screen.model.Status
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class OrdersViewModel : ViewModel() {

    private val _allItems = MutableStateFlow(createInitialItems())

    private val _selectedStatus = MutableStateFlow(Status.PENDING)
    val selectedStatus: StateFlow<Status> = _selectedStatus

    val filteredItems: StateFlow<List<DeliveryItem>> = combine(
        _allItems,
        _selectedStatus
    ) { items, status ->
        items.filter { it.status == status }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Companion.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun setStatusFilter(status: Status) {
        _selectedStatus.value = status
    }

    fun updateItemStatus(item: DeliveryItem, newStatus: Status) {
        _allItems.value = _allItems.value.map {
            if (it.orderId == item.orderId) it.copy(status = newStatus) else it
        }
    }

    private fun createInitialItems() = listOf(
        DeliveryItem("#1524", "IK287368838", Status.PENDING, "$120", 1698700800000, "2"),
        DeliveryItem("#1534", "IK279368839", Status.PENDING, "$220", 1698700800000, "4"),
        DeliveryItem("#1525", "IK287368842", Status.PENDING, "$127", 1698614400000, "5"),
        DeliveryItem("#1527", "IK287489738", Status.PENDING, "$130", 1698700800000, "6"),
        DeliveryItem("#1531", "IK287313215", Status.PENDING, "$720", 1698528000000, "2"),
        DeliveryItem("#1578", "IK287368797", Status.PENDING, "$130", 1698700800000, "1"),
        DeliveryItem("#1528", "IK287368900", Status.PENDING, "$225", 1698441600000, "2"),
        DeliveryItem("#1529", "IK287348836", Status.PENDING, "$70", 1698355200000, "7")
    )
}