package com.freshtrack.di;

import com.freshtrack.data.remote.api.EdamamApi;
import com.freshtrack.data.remote.api.FoodBApi;
import com.freshtrack.data.remote.api.NutritionIXApi;
import com.freshtrack.data.remote.api.OpenFoodFactsApi;
import com.freshtrack.data.remote.api.USDAApi;
import com.freshtrack.data.repository.NutritionRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideNutritionRepositoryFactory implements Factory<NutritionRepository> {
  private final Provider<OpenFoodFactsApi> openFoodFactsApiProvider;

  private final Provider<USDAApi> usdaApiProvider;

  private final Provider<NutritionIXApi> nutritionIXApiProvider;

  private final Provider<EdamamApi> edamamApiProvider;

  private final Provider<FoodBApi> foodBApiProvider;

  public AppModule_ProvideNutritionRepositoryFactory(
      Provider<OpenFoodFactsApi> openFoodFactsApiProvider, Provider<USDAApi> usdaApiProvider,
      Provider<NutritionIXApi> nutritionIXApiProvider, Provider<EdamamApi> edamamApiProvider,
      Provider<FoodBApi> foodBApiProvider) {
    this.openFoodFactsApiProvider = openFoodFactsApiProvider;
    this.usdaApiProvider = usdaApiProvider;
    this.nutritionIXApiProvider = nutritionIXApiProvider;
    this.edamamApiProvider = edamamApiProvider;
    this.foodBApiProvider = foodBApiProvider;
  }

  @Override
  public NutritionRepository get() {
    return provideNutritionRepository(openFoodFactsApiProvider.get(), usdaApiProvider.get(), nutritionIXApiProvider.get(), edamamApiProvider.get(), foodBApiProvider.get());
  }

  public static AppModule_ProvideNutritionRepositoryFactory create(
      Provider<OpenFoodFactsApi> openFoodFactsApiProvider, Provider<USDAApi> usdaApiProvider,
      Provider<NutritionIXApi> nutritionIXApiProvider, Provider<EdamamApi> edamamApiProvider,
      Provider<FoodBApi> foodBApiProvider) {
    return new AppModule_ProvideNutritionRepositoryFactory(openFoodFactsApiProvider, usdaApiProvider, nutritionIXApiProvider, edamamApiProvider, foodBApiProvider);
  }

  public static NutritionRepository provideNutritionRepository(OpenFoodFactsApi openFoodFactsApi,
      USDAApi usdaApi, NutritionIXApi nutritionIXApi, EdamamApi edamamApi, FoodBApi foodBApi) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideNutritionRepository(openFoodFactsApi, usdaApi, nutritionIXApi, edamamApi, foodBApi));
  }
}
