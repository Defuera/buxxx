package ru.justd.bux.product.model

import ru.justd.bux.app.model.ApiService
import rx.Single
import javax.inject.Inject

class RemoteProductDataSource @Inject constructor(private val apiService: ApiService) {

    fun getListProducts(): Single<List<Product>> = apiService.getListProducts()

    fun getProduct(productId: String): Single<Product> = apiService.getProduct(productId)

}