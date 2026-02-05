package com.freshtrack.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey
    val id: String = java.util.UUID.randomUUID().toString(),
    val barcode: String? = null,
    val name: String,
    val brand: String? = null,
    val quantity: Int = 1,
    val expirationDate: Long,
    val category: String? = null,
    val imageUrl: String? = null,
    val nutritionalInfo: String? = null,
    val notificationDays: Int = 3,
    val notificationsEnabled: Boolean = true,
    val userId: String = "",
    val syncedToCloud: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)