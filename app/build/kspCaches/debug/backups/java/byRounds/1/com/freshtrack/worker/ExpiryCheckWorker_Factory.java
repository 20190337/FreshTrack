package com.freshtrack.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.freshtrack.data.repository.ProductRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class ExpiryCheckWorker_Factory {
  private final Provider<ProductRepository> repositoryProvider;

  private final Provider<NotificationHelper> notificationHelperProvider;

  public ExpiryCheckWorker_Factory(Provider<ProductRepository> repositoryProvider,
      Provider<NotificationHelper> notificationHelperProvider) {
    this.repositoryProvider = repositoryProvider;
    this.notificationHelperProvider = notificationHelperProvider;
  }

  public ExpiryCheckWorker get(Context context, WorkerParameters params) {
    return newInstance(context, params, repositoryProvider.get(), notificationHelperProvider.get());
  }

  public static ExpiryCheckWorker_Factory create(Provider<ProductRepository> repositoryProvider,
      Provider<NotificationHelper> notificationHelperProvider) {
    return new ExpiryCheckWorker_Factory(repositoryProvider, notificationHelperProvider);
  }

  public static ExpiryCheckWorker newInstance(Context context, WorkerParameters params,
      ProductRepository repository, NotificationHelper notificationHelper) {
    return new ExpiryCheckWorker(context, params, repository, notificationHelper);
  }
}
