package com.freshtrack.data.repository;

import com.freshtrack.data.local.ProductDao;
import com.freshtrack.data.remote.api.OpenFoodFactsApi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
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

  private final Provider<FirebaseFirestore> firestoreProvider;

  private final Provider<FirebaseAuth> authProvider;

  public ProductRepository_Factory(Provider<ProductDao> productDaoProvider,
      Provider<OpenFoodFactsApi> openFoodFactsApiProvider,
      Provider<FirebaseFirestore> firestoreProvider, Provider<FirebaseAuth> authProvider) {
    this.productDaoProvider = productDaoProvider;
    this.openFoodFactsApiProvider = openFoodFactsApiProvider;
    this.firestoreProvider = firestoreProvider;
    this.authProvider = authProvider;
  }

  @Override
  public ProductRepository get() {
    return newInstance(productDaoProvider.get(), openFoodFactsApiProvider.get(), firestoreProvider.get(), authProvider.get());
  }

  public static ProductRepository_Factory create(Provider<ProductDao> productDaoProvider,
      Provider<OpenFoodFactsApi> openFoodFactsApiProvider,
      Provider<FirebaseFirestore> firestoreProvider, Provider<FirebaseAuth> authProvider) {
    return new ProductRepository_Factory(productDaoProvider, openFoodFactsApiProvider, firestoreProvider, authProvider);
  }

  public static ProductRepository newInstance(ProductDao productDao,
      OpenFoodFactsApi openFoodFactsApi, FirebaseFirestore firestore, FirebaseAuth auth) {
    return new ProductRepository(productDao, openFoodFactsApi, firestore, auth);
  }
}
