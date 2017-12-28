package ru.justd.bux.search.model

import ru.justd.bux.product.model.Product
import ru.justd.bux.product.model.ProductDataSource
import rx.Single
import javax.inject.Inject

class SearchInteractor @Inject constructor(
        private val productDataSource: ProductDataSource
) {
    fun getListProducts(): Single<List<Product>> = productDataSource.getListProducts()

}