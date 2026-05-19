package com.example.a220988_yong_nelson_lab5.data

import kotlinx.coroutines.flow.Flow

class OfflineItemsRepository(private val itemDao: ItemDao) : ItemsRepository {
    override fun getAllItemsStream(): Flow<List<Item>> = itemDao.getAllItems()

    // Fixed: Added the 'suspend' keyword to match the interface definition
    override suspend fun insertItem(item: Item) = itemDao.insert(item)
}