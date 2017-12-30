package ru.justd.bux.product.model

import ru.justd.bux.productslist.model.ProductsRepository
import javax.inject.Inject

class ProductInteractor @Inject constructor(
        private val repository: ProductsRepository
) {

    fun getProduct(productId: String) = repository.getProduct(productId)

//    fun observeProduct(productId: String) = repository.observeProduct(productId)


}