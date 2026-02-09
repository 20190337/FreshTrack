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
public final class ProductDetailViewModel_Factory implements Factory<ProductDetailViewModel> {
  private final Provider<ProductRepository> repositoryProvider;

  public ProductDetailViewModel_Factory(Provider<ProductRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public ProductDetailViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static ProductDetailViewModel_Factory create(
      Provider<ProductRepository> repositoryProvider) {
    return new ProductDetailViewModel_Factory(repositoryProvider);
  }

  public static ProductDetailViewModel newInstance(ProductRepository repository) {
    return new ProductDetailViewModel(repository);
  }
}
