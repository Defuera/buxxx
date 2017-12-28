package ru.justd.bux.product.model

import ru.justd.bux.model.ApiService
import rx.Single
import javax.inject.Inject

class ProductDataSource @Inject constructor(private val apiService: ApiService) {

    fun getListProducts(): Single<List<Product>> = apiService.getListProducts()

}