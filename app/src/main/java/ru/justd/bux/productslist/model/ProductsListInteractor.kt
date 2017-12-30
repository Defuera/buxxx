package ru.justd.bux.productslist.model

import ru.justd.bux.product.model.Product
import rx.Single
import javax.inject.Inject

class ProductsListInteractor @Inject constructor(
        private val repository: ProductsRepository
) {

    fun getListProducts(): Single<List<Product>> = repository.getListProducts()

    fun  getProduct(productId: String): Single<Product> = repository.getProduct(productId)

}