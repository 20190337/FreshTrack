package com.freshtrack.data.local

import androidx.room.*
import com.freshtrack.data.local.entities.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM products WHERE userId = :userId ORDER BY expirationDate ASC")
    fun getAllProducts(userId: String): Flow<List<Product>>
    
    @Query("SELECT * FROM products WHERE userId = :userId AND expirationDate <= :date ORDER BY expirationDate ASC")
    suspend fun getExpiringProducts(userId: String, date: Long): List<Product>
    
    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getProductById(id: String): Product?
    
    @Query("SELECT * FROM products WHERE barcode = :barcode AND userId = :userId LIMIT 1")
    suspend fun getProductByBarcode(barcode: String, userId: String): Product?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)
    
    @Update
    suspend fun updateProduct(product: Product)
    
    @Delete
    suspend fun deleteProduct(product: Product)
    
    @Query("SELECT * FROM products WHERE name LIKE '%' || :query || '%' AND userId = :userId")
    fun searchProducts(query: String, userId: String): Flow<List<Product>>
    
    @Query("UPDATE products SET syncedToCloud = 1 WHERE id = :id")
    suspend fun markAsSynced(id: String)
}