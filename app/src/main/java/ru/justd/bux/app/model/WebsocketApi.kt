package ru.justd.bux.app.model

import rx.Observable

interface WebsocketApi {

    fun observeProduct(productId: String): Observable<TradingQuote>

}