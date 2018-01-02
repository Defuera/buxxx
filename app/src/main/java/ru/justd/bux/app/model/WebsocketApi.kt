package ru.justd.bux.app.model

import rx.Observable

interface WebsocketApi {

    fun connect()

    fun observeProduct(productId: String): Observable<TradingQuote>

    fun unsubscribe(productId: String)

}