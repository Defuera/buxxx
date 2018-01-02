package ru.justd.bux.product.model

import ru.justd.bux.productslist.model.ProductsRepository
import javax.inject.Inject

class ProductInteractor @Inject constructor(
        private val repository: ProductsRepository,
        private val feed : Feed
) {

    fun getProduct(productId: String) = repository.getProduct(productId)

    fun subscribeToUpdates(productId: String) = feed.observeProduct(productId)

    fun unsubscribeFromUpdates(productId: String) = feed.unsubscribeFromUpdates(productId)

}