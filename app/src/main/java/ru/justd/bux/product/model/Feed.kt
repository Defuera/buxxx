package ru.justd.bux.product.model

import ru.justd.bux.app.model.TradingQuote
import ru.justd.bux.app.model.WebsocketApi
import rx.Observable

class Feed(
        private val websocketApi: WebsocketApi
) {

    fun connect() {
        websocketApi.connect()
    }

    fun observeProduct(productId: String): Observable<TradingQuote> = websocketApi.observeProduct(productId)

    fun unsubscribeFromUpdates(productId: String) {
        websocketApi.unsubscribe(productId)
    }

}