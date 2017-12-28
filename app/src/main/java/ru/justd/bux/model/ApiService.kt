package ru.justd.bux.model

import retrofit2.http.GET
import retrofit2.http.Path
import ru.justd.bux.product.model.Product
import rx.Single

/**
 * Created by defuera on 01/02/2017.
 */
interface ApiService {

    @GET("core/16/products/{productId}")
    fun getProduct(
            @Path("productId") productId: String
    )

    @GET("core/16/products")
    fun getListProducts(): Single<List<Product>>

}