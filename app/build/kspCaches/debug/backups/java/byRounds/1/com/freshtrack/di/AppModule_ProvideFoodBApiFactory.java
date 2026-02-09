package com.freshtrack.di;

import com.freshtrack.data.remote.api.FoodBApi;
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
public final class AppModule_ProvideFoodBApiFactory implements Factory<FoodBApi> {
  private final Provider<OkHttpClient> clientProvider;

  public AppModule_ProvideFoodBApiFactory(Provider<OkHttpClient> clientProvider) {
    this.clientProvider = clientProvider;
  }

  @Override
  public FoodBApi get() {
    return provideFoodBApi(clientProvider.get());
  }

  public static AppModule_ProvideFoodBApiFactory create(Provider<OkHttpClient> clientProvider) {
    return new AppModule_ProvideFoodBApiFactory(clientProvider);
  }

  public static FoodBApi provideFoodBApi(OkHttpClient client) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideFoodBApi(client));
  }
}
