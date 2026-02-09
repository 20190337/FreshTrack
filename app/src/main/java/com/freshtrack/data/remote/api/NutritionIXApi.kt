package com.freshtrack.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Query

interface NutritionIXApi {
    @GET("v2/search/instant")
    suspend fun searchFoods(@Query("query") query: String): NutritionIXResponse
}

data class NutritionIXResponse(
    val common: List<CommonFood>?
) {
    data class CommonFood(
        val food_name: String?,
        val photo: Photo?
    )
    
    data class Photo(
        val thumb: String?
    )
}
