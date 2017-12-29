package ru.justd.bux.product.model

import javax.inject.Inject

class ProductInteractor @Inject constructor(
        private val dataSource: ProductDataSource
) {

    fun getProduct(productId: String) = dataSource.getProduct(productId)

    fun observeProduct(productId: String) = dataSource.observeProduct(productId)


}