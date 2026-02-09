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
public final class AddProductViewModel_Factory implements Factory<AddProductViewModel> {
  private final Provider<NutritionRepository> nutritionRepositoryProvider;

  private final Provider<ProductRepository> productRepositoryProvider;

  public AddProductViewModel_Factory(Provider<NutritionRepository> nutritionRepositoryProvider,
      Provider<ProductRepository> productRepositoryProvider) {
    this.nutritionRepositoryProvider = nutritionRepositoryProvider;
    this.productRepositoryProvider = productRepositoryProvider;
  }

  @Override
  public AddProductViewModel get() {
    return newInstance(nutritionRepositoryProvider.get(), productRepositoryProvider.get());
  }

  public static AddProductViewModel_Factory create(
      Provider<NutritionRepository> nutritionRepositoryProvider,
      Provider<ProductRepository> productRepositoryProvider) {
    return new AddProductViewModel_Factory(nutritionRepositoryProvider, productRepositoryProvider);
  }

  public static AddProductViewModel newInstance(NutritionRepository nutritionRepository,
      ProductRepository productRepository) {
    return new AddProductViewModel(nutritionRepository, productRepository);
  }
}
