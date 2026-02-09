package com.freshtrack.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Path

interface FoodBApi {
    @GET("api/foods/{id}")
    suspend fun getFood(@Path("id") id: String): FoodBResponse
}

data class FoodBResponse(
    val name: String?,
    val description: String?
)
