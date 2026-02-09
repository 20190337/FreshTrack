package com.freshtrack.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Query

interface USDAApi {
    @GET("v1/foods/search")
    suspend fun searchFoods(@Query("query") query: String): USDAResponse
}

data class USDAResponse(
    val foods: List<Food>?
) {
    data class Food(
        val description: String?,
        val foodNutrients: List<Nutrient>?
    )
    
    data class Nutrient(
        val nutrientName: String?,
        val value: Double?
    )
}
