package com.freshtrack.ui.viewmodel;

import com.freshtrack.data.repository.NutritionRepository;
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
public final class ProductViewModel_Factory implements Factory<ProductViewModel> {
  private final Provider<ProductRepository> productRepositoryProvider;

  private final Provider<NutritionRepository> nutritionRepositoryProvider;

  public ProductViewModel_Factory(Provider<ProductRepository> productRepositoryProvider,
      Provider<NutritionRepository> nutritionRepositoryProvider) {
    this.productRepositoryProvider = productRepositoryProvider;
    this.nutritionRepositoryProvider = nutritionRepositoryProvider;
  }

  @Override
  public ProductViewModel get() {
    return newInstance(productRepositoryProvider.get(), nutritionRepositoryProvider.get());
  }

  public static ProductViewModel_Factory create(
      Provider<ProductRepository> productRepositoryProvider,
      Provider<NutritionRepository> nutritionRepositoryProvider) {
    return new ProductViewModel_Factory(productRepositoryProvider, nutritionRepositoryProvider);
  }

  public static ProductViewModel newInstance(ProductRepository productRepository,
      NutritionRepository nutritionRepository) {
    return new ProductViewModel(productRepository, nutritionRepository);
  }
}
