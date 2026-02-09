package com.freshtrack.di;

import com.freshtrack.data.remote.api.USDAApi;
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
public final class AppModule_ProvideUSDAApiFactory implements Factory<USDAApi> {
  private final Provider<OkHttpClient> clientProvider;

  public AppModule_ProvideUSDAApiFactory(Provider<OkHttpClient> clientProvider) {
    this.clientProvider = clientProvider;
  }

  @Override
  public USDAApi get() {
    return provideUSDAApi(clientProvider.get());
  }

  public static AppModule_ProvideUSDAApiFactory create(Provider<OkHttpClient> clientProvider) {
    return new AppModule_ProvideUSDAApiFactory(clientProvider);
  }

  public static USDAApi provideUSDAApi(OkHttpClient client) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideUSDAApi(client));
  }
}
