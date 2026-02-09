package com.freshtrack.data.repository

import android.util.Log
import com.freshtrack.data.remote.api.*
import com.freshtrack.data.remote.model.Nutriments
import com.freshtrack.data.remote.model.ProductInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NutritionRepository @Inject constructor(
    private val openFoodFactsApi: OpenFoodFactsApi,
    private val usdaApi: USDAApi,
    private val nutritionIXApi: NutritionIXApi,
    private val edamamApi: EdamamApi,
    private val foodBApi: FoodBApi
) {

    data class UnifiedProductInfo(
        val name: String?,
        val brand: String?,
        val nutriments: Nutriments?,
        val source: String
    )

    suspend fun searchAllDatabases(barcode: String): Result<UnifiedProductInfo> = withContext(Dispatchers.IO) {
        val errors = mutableListOf<String>()

        // 1. Try Open Food Facts (Free, most comprehensive)
        try {
            Log.d("NutritionRepository", "Trying Open Food Facts for: $barcode")
            val response = openFoodFactsApi.getProduct(barcode)
            if (response.product != null) {
                Log.d("NutritionRepository", "Found in Open Food Facts: ${response.product.product_name}, brand: ${response.product.brands}")
                
                // Convert kJ to kcal if needed (1 kcal = 4.184 kJ)
                val nutriments = response.product.nutriments
                val energyKcal = nutriments?.energy_kcal_100g 
                    ?: nutriments?.energy_100g?.div(4.184)
                
                // Clean up name - remove brand from name if it's there
                var cleanName = response.product.product_name ?: ""
                val brand = response.product.brands
                
                if (brand != null && cleanName.contains(brand, ignoreCase = true)) {
                    cleanName = cleanName.replace(brand, "", ignoreCase = true).trim()
                    // Remove extra spaces or commas
                    cleanName = cleanName.replace(Regex("^[,\\s]+"), "").replace(Regex("[,\\s]+$"), "")
                }
                
                return@withContext Result.success(
                    UnifiedProductInfo(
                        name = cleanName.ifBlank { response.product.product_name },
                        brand = brand,
                        nutriments = Nutriments(
                            energy_100g = energyKcal,
                            energy_kcal_100g = energyKcal,
                            proteins_100g = nutriments?.proteins_100g,
                            carbohydrates_100g = nutriments?.carbohydrates_100g,
                            fat_100g = nutriments?.fat_100g,
                            fiber_100g = nutriments?.fiber_100g,
                            sugars_100g = nutriments?.sugars_100g,
                            salt_100g = nutriments?.salt_100g
                        ),
                        source = "Open Food Facts"
                    )
                )
            }
        } catch (e: Exception) {
            errors.add("Open Food Facts: ${e.message}")
            Log.e("NutritionRepository", "Open Food Facts error: ${e.message}")
        }

        // 2. Try USDA FoodData Central
        try {
            Log.d("NutritionRepository", "Trying USDA for: $barcode")
            val response = usdaApi.searchFoods(barcode)
            if (!response.foods.isNullOrEmpty()) {
                val food = response.foods.first()
                Log.d("NutritionRepository", "Found in USDA")
                
                val nutrients = food.foodNutrients ?: emptyList()
                val calories = nutrients.find { it.nutrientName?.contains("Energy") == true }?.value
                val protein = nutrients.find { it.nutrientName?.contains("Protein") == true }?.value
                val carbs = nutrients.find { it.nutrientName?.contains("Carbohydrate") == true }?.value
                val fat = nutrients.find { it.nutrientName?.contains("Total lipid") == true }?.value
                
                return@withContext Result.success(
                    UnifiedProductInfo(
                        name = food.description,
                        brand = null,
                        nutriments = Nutriments(
                            energy_100g = calories,
                            energy_kcal_100g = calories,
                            proteins_100g = protein,
                            carbohydrates_100g = carbs,
                            fat_100g = fat,
                            fiber_100g = null,
                            sugars_100g = null,
                            salt_100g = null
                        ),
                        source = "USDA FoodData Central"
                    )
                )
            }
        } catch (e: Exception) {
            errors.add("USDA: ${e.message}")
        }

        // 3. Try NutritionIX
        try {
            Log.d("NutritionRepository", "Trying NutritionIX for: $barcode")
            val response = nutritionIXApi.searchFoods(barcode)
            if (!response.common.isNullOrEmpty()) {
                val food = response.common.first()
                Log.d("NutritionRepository", "Found in NutritionIX")
                return@withContext Result.success(
                    UnifiedProductInfo(
                        name = food.food_name,
                        brand = null,
                        nutriments = null,
                        source = "NutritionIX"
                    )
                )
            }
        } catch (e: Exception) {
            errors.add("NutritionIX: ${e.message}")
        }

        // 4. Try Edamam
        try {
            Log.d("NutritionRepository", "Trying Edamam for: $barcode")
            val response = edamamApi.parseFood(barcode)
            if (!response.parsed.isNullOrEmpty()) {
                val food = response.parsed.firstOrNull()?.food
                Log.d("NutritionRepository", "Found in Edamam")
                return@withContext Result.success(
                    UnifiedProductInfo(
                        name = food?.label,
                        brand = null,
                        nutriments = food?.nutrients?.let {
                            Nutriments(
                                energy_100g = it.ENERC_KCAL,
                                energy_kcal_100g = it.ENERC_KCAL,
                                proteins_100g = it.PROCNT,
                                carbohydrates_100g = it.CHOCDF,
                                fat_100g = it.FAT,
                                fiber_100g = null,
                                sugars_100g = null,
                                salt_100g = null
                            )
                        },
                        source = "Edamam"
                    )
                )
            }
        } catch (e: Exception) {
            errors.add("Edamam: ${e.message}")
        }

        // 5. Try FoodB.ca
        try {
            Log.d("NutritionRepository", "Trying FoodB.ca for: $barcode")
            val response = foodBApi.getFood(barcode)
            if (!response.name.isNullOrBlank()) {
                Log.d("NutritionRepository", "Found in FoodB.ca")
                return@withContext Result.success(
                    UnifiedProductInfo(
                        name = response.name,
                        brand = null,
                        nutriments = null,
                        source = "FoodB.ca"
                    )
                )
            }
        } catch (e: Exception) {
            errors.add("FoodB.ca: ${e.message}")
        }

        val errorMsg = "Product not found in any database. Tried: ${errors.joinToString(", ")}"
        Log.e("NutritionRepository", errorMsg)
        return@withContext Result.failure(Exception(errorMsg))
    }

    fun convertToProductInfo(unified: UnifiedProductInfo): ProductInfo {
        return ProductInfo(
            product_name = unified.name,
            brands = unified.brand,
            nutriments = unified.nutriments,
            categories = null,
            image_url = null,
            ingredients_text = null,
            allergens = null
        )
    }
}
