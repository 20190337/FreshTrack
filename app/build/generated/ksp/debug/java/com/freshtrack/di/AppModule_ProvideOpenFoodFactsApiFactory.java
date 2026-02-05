package com.freshtrack.di;

import com.freshtrack.data.remote.api.OpenFoodFactsApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;

@ScopeMetadata("javax.inject.Singleton")
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
public final class AppModule_ProvideOpenFoodFactsApiFactory implements Factory<OpenFoodFactsApi> {
  private final Provider<OkHttpClient> clientProvider;

  public AppModule_ProvideOpenFoodFactsApiFactory(Provider<OkHttpClient> clientProvider) {
    this.clientProvider = clientProvider;
  }

  @Override
  public OpenFoodFactsApi get() {
    return provideOpenFoodFactsApi(clientProvider.get());
  }

  public static AppModule_ProvideOpenFoodFactsApiFactory create(
      Provider<OkHttpClient> clientProvider) {
    return new AppModule_ProvideOpenFoodFactsApiFactory(clientProvider);
  }

  public static OpenFoodFactsApi provideOpenFoodFactsApi(OkHttpClient client) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideOpenFoodFactsApi(client));
  }
}
