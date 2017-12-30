package ru.justd.bux.productslist.model

import ru.justd.bux.product.model.Product
import rx.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InMemoryProductDataSource @Inject constructor() {

    val cachedProducts = ArrayList<Product>()

    fun getListProducts() = Single.just(cachedProducts)

    fun cache(products: List<Product>) {
        cachedProducts.addAll(products)
    }

    fun getProduct(productId: String): Single<Product?> = Single.just(cachedProducts.find { it.securityId == productId })

}