package com.freshtrack.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freshtrack.data.local.entities.Product
import com.freshtrack.data.remote.model.ProductInfo
import com.freshtrack.data.repository.NutritionRepository
import com.freshtrack.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val nutritionRepository: NutritionRepository
) : ViewModel() {

    private val _productInfo = MutableStateFlow<ProductInfo?>(null)
    val productInfo: StateFlow<ProductInfo?> = _productInfo

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _dataSource = MutableStateFlow<String?>(null)
    val dataSource: StateFlow<String?> = _dataSource

    fun fetchProductInfo(barcode: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            _dataSource.value = null
            
            nutritionRepository.searchAllDatabases(barcode)
                .onSuccess { unifiedInfo ->
                    _productInfo.value = nutritionRepository.convertToProductInfo(unifiedInfo)
                    _dataSource.value = unifiedInfo.source
                }
                .onFailure { 
                    _error.value = it.message
                    _productInfo.value = null
                }
            
            _isLoading.value = false
        }
    }

    fun addProduct(product: Product) {
        viewModelScope.launch {
            productRepository.addProduct(product)
        }
    }

    fun clearProductInfo() {
        _productInfo.value = null
        _error.value = null
        _dataSource.value = null
    }
}
