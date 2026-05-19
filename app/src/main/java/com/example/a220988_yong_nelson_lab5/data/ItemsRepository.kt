package com.example.a220988_yong_nelson_lab5.data

import kotlinx.coroutines.flow.Flow

interface ItemsRepository {
    fun getAllItemsStream(): Flow<List<Item>>
    suspend fun insertItem(item: Item)
}