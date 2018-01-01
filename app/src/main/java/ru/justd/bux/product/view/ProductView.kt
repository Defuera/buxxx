package ru.justd.bux.product.view

import ru.justd.bux.app.model.TradingQuote
import ru.justd.bux.product.model.Product

interface ProductView {

    fun getProductId(): String

    fun showData(product: Product)

    fun showError(throwable: Throwable)

    fun updatePrice(quote: TradingQuote)

}