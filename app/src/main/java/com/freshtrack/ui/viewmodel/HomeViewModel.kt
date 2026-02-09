package com.freshtrack.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freshtrack.data.local.entities.Product
import com.freshtrack.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    val expiringProducts: Flow<List<Product>> = repository.getAllProducts()
        .map { products ->
            val currentTime = System.currentTimeMillis()
            products.filter { product ->
                val daysUntilExpiry = TimeUnit.MILLISECONDS.toDays(
                    product.expirationDate - currentTime
                )
                daysUntilExpiry <= product.notificationDays && daysUntilExpiry >= 0
            }.sortedBy { it.expirationDate }
        }

    val allProductsCount: Flow<Int> = repository.getAllProducts()
        .map { it.size }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            repository.deleteProduct(product)
        }
    }
}
