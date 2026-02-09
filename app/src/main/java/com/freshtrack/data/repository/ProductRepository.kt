package com.freshtrack.data.repository

import com.freshtrack.data.local.ProductDao
import com.freshtrack.data.local.entities.Product
import com.freshtrack.data.remote.api.OpenFoodFactsApi
import com.freshtrack.data.remote.model.ProductInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(
    private val productDao: ProductDao,
    private val openFoodFactsApi: OpenFoodFactsApi
) {
    private val defaultUserId = "local_user"

    fun getAllProducts(): Flow<List<Product>> {
        return productDao.getAllProducts(defaultUserId)
    }

    fun getProductById(productId: String): Flow<Product?> = flow {
        val products = productDao.getAllProducts(defaultUserId)
        products.collect { productList ->
            emit(productList.find { it.id == productId })
        }
    }

    suspend fun getProductByBarcode(barcode: String): Product? {
        return productDao.getProductByBarcode(barcode, defaultUserId)
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
        val productWithUser = product.copy(userId = defaultUserId)
        productDao.insertProduct(productWithUser)
    }

    suspend fun updateProduct(product: Product) {
        val updated = product.copy(updatedAt = System.currentTimeMillis())
        productDao.updateProduct(updated)
    }

    suspend fun deleteProduct(product: Product) {
        productDao.deleteProduct(product)
    }

    suspend fun getExpiringProducts(days: Int): List<Product> {
        val threshold = System.currentTimeMillis() + (days * 24 * 60 * 60 * 1000)
        return productDao.getExpiringProducts(defaultUserId, threshold)
    }
}
