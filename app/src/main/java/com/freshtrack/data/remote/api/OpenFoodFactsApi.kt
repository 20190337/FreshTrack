package com.freshtrack.data.remote.api

import com.freshtrack.data.remote.model.OpenFoodFactsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface OpenFoodFactsApi {
    @GET("api/v0/product/{barcode}.json")
    suspend fun getProduct(@Path("barcode") barcode: String): OpenFoodFactsResponse
}