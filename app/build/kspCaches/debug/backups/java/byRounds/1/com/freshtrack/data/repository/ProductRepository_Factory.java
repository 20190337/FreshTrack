package com.freshtrack.data.repository;

import com.freshtrack.data.local.ProductDao;
import com.freshtrack.data.remote.api.OpenFoodFactsApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class ProductRepository_Factory implements Factory<ProductRepository> {
  private final Provider<ProductDao> productDaoProvider;

  private final Provider<OpenFoodFactsApi> openFoodFactsApiProvider;

  public ProductRepository_Factory(Provider<ProductDao> productDaoProvider,
      Provider<OpenFoodFactsApi> openFoodFactsApiProvider) {
    this.productDaoProvider = productDaoProvider;
    this.openFoodFactsApiProvider = openFoodFactsApiProvider;
  }

  @Override
  public ProductRepository get() {
    return newInstance(productDaoProvider.get(), openFoodFactsApiProvider.get());
  }

  public static ProductRepository_Factory create(Provider<ProductDao> productDaoProvider,
      Provider<OpenFoodFactsApi> openFoodFactsApiProvider) {
    return new ProductRepository_Factory(productDaoProvider, openFoodFactsApiProvider);
  }

  public static ProductRepository newInstance(ProductDao productDao,
      OpenFoodFactsApi openFoodFactsApi) {
    return new ProductRepository(productDao, openFoodFactsApi);
  }
}
