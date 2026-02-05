package com.freshtrack.data.repository

import com.freshtrack.data.local.ProductDao
import com.freshtrack.data.local.entities.Product
import com.freshtrack.data.remote.api.OpenFoodFactsApi
import com.freshtrack.data.remote.model.ProductInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(
    private val productDao: ProductDao,
    private val openFoodFactsApi: OpenFoodFactsApi,
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {
    private val currentUserId: String?
        get() = auth.currentUser?.uid

    fun getAllProducts(): Flow<List<Product>> {
        return productDao.getAllProducts(currentUserId ?: "")
    }

    suspend fun getProductByBarcode(barcode: String): Product? {
        return productDao.getProductByBarcode(barcode, currentUserId ?: "")
    }

    suspend fun fetchProductInfo(barcode: String): Result<ProductInfo> {
        return try {
            val response = openFoodFactsApi.getProduct(barcode)
            if (response.status == 1 && response.product != null) {
                Result.success(response.product)
            } else {
                Result.failure(Exception("Product not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun addProduct(product: Product) {
        val productWithUser = product.copy(userId = currentUserId ?: "")
        productDao.insertProduct(productWithUser)
        syncToCloud(productWithUser)
    }

    suspend fun updateProduct(product: Product) {
        val updated = product.copy(updatedAt = System.currentTimeMillis())
        productDao.updateProduct(updated)
        syncToCloud(updated)
    }

    suspend fun deleteProduct(product: Product) {
        productDao.deleteProduct(product)
        deleteFromCloud(product.id)
    }

    suspend fun getExpiringProducts(days: Int): List<Product> {
        val threshold = System.currentTimeMillis() + (days * 24 * 60 * 60 * 1000)
        return productDao.getExpiringProducts(currentUserId ?: "", threshold)
    }

    private suspend fun syncToCloud(product: Product) {
        try {
            currentUserId?.let { uid ->
                firestore.collection("users")
                    .document(uid)
                    .collection("products")
                    .document(product.id)
                    .set(product)
                    .await()
                productDao.markAsSynced(product.id)
            }
        } catch (e: Exception) {
            // Handle sync failure
        }
    }

    private suspend fun deleteFromCloud(productId: String) {
        try {
            currentUserId?.let { uid ->
                firestore.collection("users")
                    .document(uid)
                    .collection("products")
                    .document(productId)
                    .delete()
                    .await()
            }
        } catch (e: Exception) {
            // Handle deletion failure
        }
    }

    suspend fun syncFromCloud() {
        try {
            currentUserId?.let { uid ->
                val snapshot = firestore.collection("users")
                    .document(uid)
                    .collection("products")
                    .get()
                    .await()
                
                val products = snapshot.toObjects(Product::class.java)
                products.forEach { productDao.insertProduct(it) }
            }
        } catch (e: Exception) {
            // Handle fetch failure
        }
    }
}