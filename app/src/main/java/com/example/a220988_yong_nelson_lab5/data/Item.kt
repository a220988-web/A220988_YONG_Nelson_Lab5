package com.example.a220988_yong_nelson_lab5.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val quantity: Int,
    val flavor: String,
    val date: String,
    val price: String
)