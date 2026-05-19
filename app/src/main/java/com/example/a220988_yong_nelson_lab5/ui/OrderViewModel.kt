package com.example.a220988_yong_nelson_lab5.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a220988_yong_nelson_lab5.data.Item
import com.example.a220988_yong_nelson_lab5.data.ItemsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

data class OrderUiState(
    val quantity: Int = 0,
    val flavor: String = "",
    val date: String = "",
    val price: String = "$0.00"
)

private const val PRICE_PER_CUPCAKE = 2.00
private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00

class OrderViewModel(private val itemsRepository: ItemsRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(OrderUiState())
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    // Connects live view stream directly to Room DB
    val orderHistory: StateFlow<List<Item>> = itemsRepository.getAllItemsStream()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    val dateOptions: List<String> = pickupOptions()

    fun setQuantity(numberCupcakes: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                quantity = numberCupcakes,
                flavor = if (currentState.flavor.isEmpty()) "Vanilla" else currentState.flavor
            )
        }
        updatePrice()
    }

    fun setFlavor(desiredFlavor: String) {
        _uiState.update { currentState ->
            currentState.copy(flavor = desiredFlavor)
        }
    }

    fun setDate(pickupDate: String) {
        _uiState.update { currentState ->
            currentState.copy(date = pickupDate)
        }
        updatePrice()
    }

    fun resetOrder() {
        _uiState.value = OrderUiState()
    }

    fun completeOrder() {
        val current = _uiState.value
        if (current.quantity > 0) {
            viewModelScope.launch {
                itemsRepository.insertItem(
                    Item(
                        quantity = current.quantity,
                        flavor = current.flavor,
                        date = current.date,
                        price = current.price
                    )
                )
            }
        }
    }

    private fun updatePrice() {
        val currentQuantity = _uiState.value.quantity
        var calculatedPrice = currentQuantity * PRICE_PER_CUPCAKE
        if (_uiState.value.date == dateOptions[0]) {
            calculatedPrice += PRICE_FOR_SAME_DAY_PICKUP
        }
        val formattedPrice = String.format("$%.2f", calculatedPrice)
        _uiState.update { currentState ->
            currentState.copy(price = formattedPrice)
        }
    }

    private fun pickupOptions(): List<String> {
        val options = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()
        repeat(4) {
            options.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return options
    }
}