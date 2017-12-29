package ru.justd.bux

import android.content.Context
import ru.justd.bux.product.ProductActivity

class Router {

    fun showDetailedProduct(context: Context, productId: String) {
        ProductActivity.start(context, productId)
    }

}