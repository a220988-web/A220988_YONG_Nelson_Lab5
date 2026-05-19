package com.example.a220988_yong_nelson_lab5.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a220988_yong_nelson_lab5.data.Item
import com.example.a220988_yong_nelson_lab5.ui.OrderUiState

@Composable
fun StartOrderScreen(
    onNextButtonClicked: (Int) -> Unit,
    onCheckHistoryClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(Color(0xFFFFF1F5), shape = RoundedCornerShape(100.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("🧁", fontSize = 96.sp)
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Order Cupcakes",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF4A148C)
        )
        Spacer(modifier = Modifier.height(48.dp))

        val buttonColors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C27B0))
        Button(
            onClick = { onNextButtonClicked(1) },
            colors = buttonColors,
            modifier = Modifier.fillMaxWidth(0.8f).height(48.dp)
        ) {
            Text("One Cupcake", fontSize = 16.sp, color = Color.White)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onNextButtonClicked(6) },
            colors = buttonColors,
            modifier = Modifier.fillMaxWidth(0.8f).height(48.dp)
        ) {
            Text("Six Cupcakes", fontSize = 16.sp, color = Color.White)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onNextButtonClicked(12) },
            colors = buttonColors,
            modifier = Modifier.fillMaxWidth(0.8f).height(48.dp)
        ) {
            Text("Twelve Cupcakes", fontSize = 16.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(24.dp))
        OutlinedButton(
            onClick = onCheckHistoryClicked,
            modifier = Modifier.fillMaxWidth(0.8f).height(48.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF9C27B0))
        ) {
            Text("Check Order History", fontSize = 16.sp)
        }
    }
}

@Composable
fun SelectFlavorScreen(
    subtotal: String,
    selectedFlavor: String,
    options: List<String> = listOf("Vanilla", "Chocolate", "Red Velvet", "Salted Caramel", "Coffee"),
    onSelectionChanged: (String) -> Unit,
    onNextButtonClicked: () -> Unit,
    onCancelButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            options.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (selectedFlavor == item),
                            onClick = { onSelectionChanged(item) }
                        )
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (selectedFlavor == item),
                        onClick = { onSelectionChanged(item) },
                        colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF9C27B0))
                    )
                    Text(item, modifier = Modifier.padding(start = 16.dp), fontSize = 18.sp)
                }
            }
            Divider(modifier = Modifier.padding(vertical = 16.dp))
            Text(
                text = "Subtotal $subtotal",
                modifier = Modifier.fillMaxWidth().padding(end = 16.dp),
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = onCancelButtonClicked,
                modifier = Modifier.weight(1f).height(48.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF9C27B0))
            ) {
                Text("Cancel")
            }
            Button(
                onClick = onNextButtonClicked,
                enabled = selectedFlavor.isNotEmpty(),
                modifier = Modifier.weight(1f).height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C27B0))
            ) {
                Text("Next")
            }
        }
    }
}

@Composable
fun SelectDateScreen(
    subtotal: String,
    selectedDate: String,
    options: List<String>,
    onSelectionChanged: (String) -> Unit,
    onNextButtonClicked: () -> Unit,
    onCancelButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            options.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (selectedDate == item),
                            onClick = { onSelectionChanged(item) }
                        )
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (selectedDate == item),
                        onClick = { onSelectionChanged(item) },
                        colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF9C27B0))
                    )
                    Text(item, modifier = Modifier.padding(start = 16.dp), fontSize = 18.sp)
                }
            }
            Divider(modifier = Modifier.padding(vertical = 16.dp))
            Text(
                text = "Subtotal $subtotal",
                modifier = Modifier.fillMaxWidth().padding(end = 16.dp),
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = onCancelButtonClicked,
                modifier = Modifier.weight(1f).height(48.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF9C27B0))
            ) {
                Text("Cancel")
            }
            Button(
                onClick = onNextButtonClicked,
                enabled = selectedDate.isNotEmpty(),
                modifier = Modifier.weight(1f).height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C27B0))
            ) {
                Text("Next")
            }
        }
    }
}

@Composable
fun OrderSummaryScreen(
    orderUiState: OrderUiState,
    onCancelButtonClicked: () -> Unit,
    onSendButtonClicked: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("QUANTITY", color = Color.Gray, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text("${orderUiState.quantity} cupcake" + if (orderUiState.quantity > 1) "s" else "", fontSize = 18.sp)
            Divider()

            Text("FLAVOR", color = Color.Gray, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text(orderUiState.flavor, fontSize = 18.sp)
            Divider()

            Text("PICKUP DATE", color = Color.Gray, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text(orderUiState.date, fontSize = 18.sp)
            Divider()

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Subtotal ${orderUiState.price}",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val shareSubject = "New Cupcake Order"
            val shareText = "Quantity: ${orderUiState.quantity} cupcake\nFlavor: ${orderUiState.flavor}\nPickup date: ${orderUiState.date}\nTotal: ${orderUiState.price}\n\nThank you!"

            Button(
                onClick = { onSendButtonClicked(shareSubject, shareText) },
                modifier = Modifier.fillMaxWidth().height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C27B0))
            ) {
                Text("Send Order to Another App", color = Color.White)
            }

            OutlinedButton(
                onClick = onCancelButtonClicked,
                modifier = Modifier.fillMaxWidth().height(48.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF9C27B0))
            ) {
                Text("Cancel")
            }
        }
    }
}

@Composable
fun OrderHistoryScreen(
    historyList: List<Item>,
    onResetClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        if (historyList.isEmpty()) {
            Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text("No past orders found.", color = Color.Gray, fontSize = 16.sp)
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(historyList) { order ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFCE4EC))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Order #${order.id}", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF4A148C))
                                Text(order.date, color = Color.Gray, fontSize = 14.sp)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("${order.quantity} cupcakes · ${order.flavor}", fontSize = 16.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Total: ${order.price}",
                                color = Color(0xFF9C27B0),
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }

        Button(
            onClick = onResetClicked,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp).height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C27B0))
        ) {
            Text("Create New Order", color = Color.White)
        }
    }
}