package com.freshtrack.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Query

interface EdamamApi {
    @GET("api/food-database/v2/parser")
    suspend fun parseFood(@Query("ingr") ingredient: String): EdamamResponse
}

data class EdamamResponse(
    val parsed: List<ParsedFood>?
) {
    data class ParsedFood(
        val food: Food?
    )
    
    data class Food(
        val label: String?,
        val nutrients: Nutrients?
    )
    
    data class Nutrients(
        val ENERC_KCAL: Double?,
        val PROCNT: Double?,
        val CHOCDF: Double?,
        val FAT: Double?
    )
}
