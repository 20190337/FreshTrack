package com.freshtrack.di

import android.content.Context
import androidx.room.Room
import com.freshtrack.data.local.AppDatabase
import com.freshtrack.data.local.preferences.ThemePreferences
import com.freshtrack.data.remote.api.*
import com.freshtrack.data.repository.NutritionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "freshtrack.db"
        ).build()
    }

    @Provides
    fun provideProductDao(database: AppDatabase) = database.productDao()

    @Provides
    @Singleton
    fun provideThemePreferences(@ApplicationContext context: Context): ThemePreferences {
        return ThemePreferences(context)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideOpenFoodFactsApi(client: OkHttpClient): OpenFoodFactsApi {
        return Retrofit.Builder()
            .baseUrl("https://world.openfoodfacts.org/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenFoodFactsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUSDAApi(client: OkHttpClient): USDAApi {
        return Retrofit.Builder()
            .baseUrl("https://api.nal.usda.gov/fdc/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(USDAApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNutritionIXApi(client: OkHttpClient): NutritionIXApi {
        return Retrofit.Builder()
            .baseUrl("https://trackapi.nutritionix.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NutritionIXApi::class.java)
    }

    @Provides
    @Singleton
    fun provideEdamamApi(client: OkHttpClient): EdamamApi {
        return Retrofit.Builder()
            .baseUrl("https://api.edamam.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EdamamApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFoodBApi(client: OkHttpClient): FoodBApi {
        return Retrofit.Builder()
            .baseUrl("http://foodb.ca/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FoodBApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNutritionRepository(
        openFoodFactsApi: OpenFoodFactsApi,
        usdaApi: USDAApi,
        nutritionIXApi: NutritionIXApi,
        edamamApi: EdamamApi,
        foodBApi: FoodBApi
    ): NutritionRepository {
        return NutritionRepository(
            openFoodFactsApi,
            usdaApi,
            nutritionIXApi,
            edamamApi,
            foodBApi
        )
    }
}
