package ru.justd.bux.productslist.model

import android.util.Log
import ru.justd.bux.app.model.TradingQuote
import ru.justd.bux.app.model.WebsocketApi
import ru.justd.bux.product.model.Product
import ru.justd.bux.product.model.RemoteProductDataSource
import rx.Observable
import rx.Single
import javax.inject.Inject

class ProductsRepository @Inject constructor(
        private val remoteProductDataSource: RemoteProductDataSource,
        private val inMemoryProductDataSource: InMemoryProductDataSource,
        private val websocketApi: WebsocketApi
) {

    fun getListProducts(): Single<List<Product>> {
        return inMemoryProductDataSource
                .getListProducts()
                .flatMap { products ->
                    if (products.isEmpty()) {
                        Log.i("ProductsRepository", "getListProducts: load remote")
                        remoteProductDataSource
                                .getListProducts()
                                .doOnSuccess { inMemoryProductDataSource.cache(it) }
                    } else {
                        Log.i("ProductsRepository", "getListProducts: load from local cache")
                        Single.just(products)
                    }
                }
    }

    fun getProduct(productId: String): Single<Product> {
        return inMemoryProductDataSource
                .getProduct(productId)
                .flatMap { product ->
                    if (product == null) {
                        remoteProductDataSource.getProduct(productId)
                    } else {
                        Single.just(product)
                    }
                }
    }

    fun observeProduct(productId: String) : Observable<TradingQuote> = websocketApi.observeProduct(productId)

}