package com.freshtrack.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.freshtrack.data.local.entities.Product

@Database(
    entities = [Product::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}