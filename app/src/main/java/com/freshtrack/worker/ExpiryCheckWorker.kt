package com.freshtrack.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.freshtrack.data.repository.ProductRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.concurrent.TimeUnit

@HiltWorker
class ExpiryCheckWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val repository: ProductRepository,
    private val notificationHelper: NotificationHelper
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val products = repository.getAllProducts()
            val currentTime = System.currentTimeMillis()

            products.collect { productList ->
                productList.forEach { product ->
                    if (product.notificationsEnabled) {
                        val daysUntilExpiry = TimeUnit.MILLISECONDS.toDays(
                            product.expirationDate - currentTime
                        )

                        if (daysUntilExpiry <= product.notificationDays && daysUntilExpiry >= 0) {
                            notificationHelper.showExpiryNotification(
                                product.name,
                                daysUntilExpiry.toInt()
                            )
                        }
                    }
                }
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}
