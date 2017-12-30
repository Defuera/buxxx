package ru.justd.bux.app

import android.content.Context
import ru.justd.bux.product.ProductActivity

class Router {

    fun showDetailedProduct(context: Context, productId: String) {
        ProductActivity.start(context, productId)
    }

}