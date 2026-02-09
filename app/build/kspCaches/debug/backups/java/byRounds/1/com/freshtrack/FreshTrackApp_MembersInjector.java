package com.freshtrack;

import androidx.hilt.work.HiltWorkerFactory;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class FreshTrackApp_MembersInjector implements MembersInjector<FreshTrackApp> {
  private final Provider<HiltWorkerFactory> workerFactoryProvider;

  public FreshTrackApp_MembersInjector(Provider<HiltWorkerFactory> workerFactoryProvider) {
    this.workerFactoryProvider = workerFactoryProvider;
  }

  public static MembersInjector<FreshTrackApp> create(
      Provider<HiltWorkerFactory> workerFactoryProvider) {
    return new FreshTrackApp_MembersInjector(workerFactoryProvider);
  }

  @Override
  public void injectMembers(FreshTrackApp instance) {
    injectWorkerFactory(instance, workerFactoryProvider.get());
  }

  @InjectedFieldSignature("com.freshtrack.FreshTrackApp.workerFactory")
  public static void injectWorkerFactory(FreshTrackApp instance, HiltWorkerFactory workerFactory) {
    instance.workerFactory = workerFactory;
  }
}
