package com.freshtrack.data.remote.model

data class OpenFoodFactsResponse(
    val code: String,
    val status: Int,
    val product: ProductInfo?,
    val status_verbose: String
)

data class ProductInfo(
    val product_name: String?,
    val brands: String?,
    val categories: String?,
    val image_url: String?,
    val nutriments: Nutriments?,
    val ingredients_text: String?,
    val allergens: String?
)

data class Nutriments(
    val energy_100g: Double?,
    val energy_kcal_100g: Double?,
    val proteins_100g: Double?,
    val carbohydrates_100g: Double?,
    val fat_100g: Double?,
    val fiber_100g: Double?,
    val sugars_100g: Double?,
    val salt_100g: Double?
)
