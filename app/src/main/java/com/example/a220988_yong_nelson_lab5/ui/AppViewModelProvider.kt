package com.example.a220988_yong_nelson_lab5.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.a220988_yong_nelson_lab5.InventoryApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            OrderViewModel(
                inventoryApplication().container.itemsRepository
            )
        }
    }
}

// Fixed: Changed CreationExtras::inventoryApplication to CreationExtras.inventoryApplication
fun CreationExtras.inventoryApplication(): InventoryApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as InventoryApplication)