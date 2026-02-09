package com.freshtrack.di;

import com.freshtrack.data.remote.api.NutritionIXApi;
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
public final class AppModule_ProvideNutritionIXApiFactory implements Factory<NutritionIXApi> {
  private final Provider<OkHttpClient> clientProvider;

  public AppModule_ProvideNutritionIXApiFactory(Provider<OkHttpClient> clientProvider) {
    this.clientProvider = clientProvider;
  }

  @Override
  public NutritionIXApi get() {
    return provideNutritionIXApi(clientProvider.get());
  }

  public static AppModule_ProvideNutritionIXApiFactory create(
      Provider<OkHttpClient> clientProvider) {
    return new AppModule_ProvideNutritionIXApiFactory(clientProvider);
  }

  public static NutritionIXApi provideNutritionIXApi(OkHttpClient client) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideNutritionIXApi(client));
  }
}
