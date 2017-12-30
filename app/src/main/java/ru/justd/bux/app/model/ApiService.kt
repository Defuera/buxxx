package ru.justd.bux.app.model

import retrofit2.http.GET
import retrofit2.http.Path
import ru.justd.bux.product.model.Product
import rx.Single

interface ApiService {

    @GET("core/16/products/{productId}")
    fun getProduct(
            @Path("productId") productId: String
    ) : Single<Product>

    @GET("core/16/products")
    fun getListProducts(): Single<List<Product>>

}