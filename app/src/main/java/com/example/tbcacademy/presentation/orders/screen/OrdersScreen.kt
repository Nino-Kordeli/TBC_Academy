package com.example.tbcacademy.presentation.orders.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composeapp.ui.theme.GrayDark
import com.example.composeapp.ui.theme.Green
import com.example.composeapp.ui.theme.LightGreen
import com.example.composeapp.ui.theme.LightOrange
import com.example.composeapp.ui.theme.LightRed
import com.example.composeapp.ui.theme.Orange
import com.example.composeapp.ui.theme.Red
import com.example.tbcacademy.R
import com.example.tbcacademy.domain.model.Order
import com.example.tbcacademy.domain.model.OrderStatus
import com.example.tbcacademy.presentation.common.BaseScreen
import com.example.tbcacademy.presentation.orders.contract.OrdersEvent
import com.example.tbcacademy.presentation.orders.vm.OrdersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreen(
    navigator: NavController,
    viewModel: OrdersViewModel = hiltViewModel()
) {
    BaseScreen(
        viewModel = viewModel,
        onSideEffect = {},
    ) { state, onEvent ->

        var selectedStatus by remember { mutableStateOf(OrderStatus.PENDING) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(com.example.composeapp.ui.theme.White)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_options),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .weight(1f)
                )

                Text(
                    text = "My Orders",
                    fontSize = 25.sp,
                    modifier = Modifier.weight(2f),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_notifications),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .weight(1f)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                val statuses =
                    listOf(OrderStatus.PENDING, OrderStatus.DELIVERED, OrderStatus.CANCELED)

                statuses.forEach { status ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 4.dp)
                            .height(40.dp)
                            .background(
                                color = if (status == selectedStatus) GrayDark else White,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .clickable {
                                selectedStatus = status
                                onEvent(OrdersEvent.StatusSelected(status))
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = status.name,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            color = if (status == selectedStatus) White else Black
                        )
                    }
                }
            }

            PullToRefreshBox(
                isRefreshing = state.isLoading,
                onRefresh = { onEvent(OrdersEvent.Refresh) },
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(
                        items = state.orders.filter { it.status == selectedStatus },
                        key = { it.id }
                    ) { order ->
                        OrderItem(
                            order = order,
                            onStatusChanged = { newStatus ->
                                onEvent(
                                    OrdersEvent.ChangeOrderStatus(
                                        orderId = order.id,
                                        newStatus = newStatus
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OrderItem(
    order: Order,
    onStatusChanged: (OrderStatus) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, top = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Order #${order.orderNumber}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(order.date, style = MaterialTheme.typography.bodyMedium, color = Gray)
            }

            Spacer(Modifier.height(8.dp))

            Text(
                modifier = Modifier.padding(start = 12.dp),
                text = "Tracking: ${order.trackingNumber}",
                style = MaterialTheme.typography.bodySmall,
                color = Gray
            )

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "Quantity: ${order.quantity}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Gray,
                )
                Text(
                    text = "Subtotal:$${order.subtotal}",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatusText(status = order.status)

                Button(
                    onClick = { showDialog = true },
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = White,
                        contentColor = Black
                    ),
                    modifier = Modifier.border(
                        width = 1.dp,
                        color = Black,
                        shape = RoundedCornerShape(18.dp)
                    )
                ) {
                    Text("Details", fontWeight = FontWeight.Bold)
                }
            }
        }
    }

    if (showDialog) {
        val allowedNextStatuses = when (order.status) {
            OrderStatus.PENDING -> listOf(OrderStatus.DELIVERED, OrderStatus.CANCELED)
            OrderStatus.DELIVERED -> listOf(OrderStatus.PENDING)
            OrderStatus.CANCELED -> emptyList()
        }

        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Change Order Status") },
            text = {
                if (allowedNextStatuses.isEmpty()) {
                    Text("This order's status cannot be changed.")
                } else {
                    Column {
                        allowedNextStatuses.forEach { status ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onStatusChanged(status)
                                        showDialog = false
                                    }
                                    .padding(vertical = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = status.name,
                                    fontWeight = FontWeight.Medium
                                )
                                Box(
                                    modifier = Modifier
                                        .size(12.dp)
                                        .background(
                                            color = when (status) {
                                                OrderStatus.PENDING -> Orange
                                                OrderStatus.DELIVERED -> Green
                                                OrderStatus.CANCELED -> Red
                                            },
                                            shape = RoundedCornerShape(50)
                                        )
                                )
                            }
                            if (status != allowedNextStatuses.last()) {
                                HorizontalDivider(
                                    Modifier,
                                    DividerDefaults.Thickness,
                                    DividerDefaults.color
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun StatusText(status: OrderStatus) {
    val (color) = when (status) {
        OrderStatus.DELIVERED -> Green to LightGreen
        OrderStatus.PENDING -> Orange to LightOrange
        OrderStatus.CANCELED -> Red to LightRed
    }

    Box(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = status.name,
            color = color,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodySmall
        )
    }
}