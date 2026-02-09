package com.freshtrack.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freshtrack.data.local.entities.Product
import com.freshtrack.data.remote.model.Nutriments
import com.freshtrack.data.repository.NutritionRepository
import com.freshtrack.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

data class AddProductUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val barcode: String = "",
    val productName: String = "",
    val brand: String = "",
    val quantity: String = "1",
    val expirationDate: String = "",
    val notificationDays: Int = 3,
    val dataSource: String = "",
    val nutriments: Nutriments? = null,
    val isSaved: Boolean = false
)

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val nutritionRepository: NutritionRepository,
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddProductUiState())
    val uiState: StateFlow<AddProductUiState> = _uiState

    fun loadProductData(barcode: String) {
        _uiState.value = _uiState.value.copy(
            barcode = barcode,
            isLoading = true,
            errorMessage = null
        )

        viewModelScope.launch {
            try {
                val result = nutritionRepository.searchAllDatabases(barcode)
                
                result.onSuccess { productInfo ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        productName = productInfo.name ?: "",
                        brand = productInfo.brand ?: "",
                        dataSource = productInfo.source,
                        nutriments = productInfo.nutriments
                    )
                }.onFailure { 
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "Could not find product. Please enter details manually."
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Error loading product: ${e.message}"
                )
            }
        }
    }

    fun onProductNameChange(name: String) {
        _uiState.value = _uiState.value.copy(productName = name)
    }

    fun onBrandChange(brand: String) {
        _uiState.value = _uiState.value.copy(brand = brand)
    }

    fun onQuantityChange(quantity: String) {
        _uiState.value = _uiState.value.copy(quantity = quantity)
    }

    fun onExpirationDateChange(date: String) {
        _uiState.value = _uiState.value.copy(expirationDate = date)
    }

    fun onNotificationDaysChange(days: Int) {
        _uiState.value = _uiState.value.copy(notificationDays = days)
    }

    fun canSave(): Boolean {
        val state = _uiState.value
        return state.productName.isNotBlank() && state.expirationDate.isNotBlank()
    }

    fun saveProduct() {
        if (!canSave()) {
            _uiState.value = _uiState.value.copy(
                errorMessage = "Product name and expiration date are required"
            )
            return
        }

        val state = _uiState.value
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        
        val expirationTimestamp = try {
            dateFormat.parse(state.expirationDate)?.time ?: System.currentTimeMillis()
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(
                errorMessage = "Invalid date format. Use YYYY-MM-DD"
            )
            return
        }

        val product = Product(
            barcode = state.barcode,
            name = state.productName,
            brand = state.brand,
            quantity = state.quantity.toIntOrNull() ?: 1,
            expirationDate = expirationTimestamp,
            notificationDays = state.notificationDays,
            nutritionalInfo = state.nutriments?.let { 
                "Energy: ${"%.1f".format(it.energy_100g)} kcal, " +
                "Proteins: ${"%.1f".format(it.proteins_100g)} g, " +
                "Carbs: ${"%.1f".format(it.carbohydrates_100g)} g, " +
                "Fat: ${"%.1f".format(it.fat_100g)} g"
            }
        )

        viewModelScope.launch {
            try {
                productRepository.addProduct(product)
                _uiState.value = _uiState.value.copy(
                    isSaved = true,
                    errorMessage = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Failed to save: ${e.message}"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}
