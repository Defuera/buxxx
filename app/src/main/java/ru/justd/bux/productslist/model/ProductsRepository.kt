package ru.justd.bux.productslist.model

import ru.justd.bux.product.model.Product
import ru.justd.bux.product.model.RemoteProductDataSource
import rx.Single
import javax.inject.Inject

class ProductsRepository @Inject constructor(
        private val remoteProductDataSource: RemoteProductDataSource,
        private val inMemoryProductDataSource: InMemoryProductDataSource
) {

    fun getListProducts(): Single<List<Product>> {
        return inMemoryProductDataSource
                .getListProducts()
                .flatMap { products ->
                    if (products.isEmpty()) {
                        remoteProductDataSource
                                .getListProducts()
                                .doOnSuccess { inMemoryProductDataSource.cache(it) }
                    } else {
                        Single.just(products)
                    }
                }
    }

    fun getProduct(productId: String): Single<Product> {
        return inMemoryProductDataSource
                .getProduct(productId)
                .flatMap { product ->
                    if (product == null) {
                        remoteProductDataSource.getProduct(productId)
                    } else {
                        Single.just(product)
                    }
                }
    }

}