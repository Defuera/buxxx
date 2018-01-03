package ru.justd.bux.product.model

import ru.justd.bux.app.model.WebsocketApi
import ru.justd.bux.productslist.model.ProductsRepository
import javax.inject.Inject

class ProductInteractor @Inject constructor(
        private val repository: ProductsRepository,
        private val websocketApi: WebsocketApi
) {

    fun getProduct(productId: String) = repository.getProduct(productId)

    fun subscribeToUpdates(productId: String) = websocketApi.observeProduct(productId)

    fun unsubscribeFromUpdates(productId: String) = websocketApi.unsubscribe(productId)

}