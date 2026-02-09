package com.freshtrack.di;

import com.freshtrack.data.remote.api.EdamamApi;
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
public final class AppModule_ProvideEdamamApiFactory implements Factory<EdamamApi> {
  private final Provider<OkHttpClient> clientProvider;

  public AppModule_ProvideEdamamApiFactory(Provider<OkHttpClient> clientProvider) {
    this.clientProvider = clientProvider;
  }

  @Override
  public EdamamApi get() {
    return provideEdamamApi(clientProvider.get());
  }

  public static AppModule_ProvideEdamamApiFactory create(Provider<OkHttpClient> clientProvider) {
    return new AppModule_ProvideEdamamApiFactory(clientProvider);
  }

  public static EdamamApi provideEdamamApi(OkHttpClient client) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideEdamamApi(client));
  }
}
