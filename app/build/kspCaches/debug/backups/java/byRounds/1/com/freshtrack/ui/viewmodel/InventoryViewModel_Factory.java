package com.freshtrack.ui.viewmodel;

import com.freshtrack.data.repository.ProductRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class InventoryViewModel_Factory implements Factory<InventoryViewModel> {
  private final Provider<ProductRepository> repositoryProvider;

  public InventoryViewModel_Factory(Provider<ProductRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public InventoryViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static InventoryViewModel_Factory create(Provider<ProductRepository> repositoryProvider) {
    return new InventoryViewModel_Factory(repositoryProvider);
  }

  public static InventoryViewModel newInstance(ProductRepository repository) {
    return new InventoryViewModel(repository);
  }
}
