package ru.justd.bux.search.view

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import ru.justd.bux.R
import ru.justd.bux.product.model.Product

class ProductWidget(context: Context) : FrameLayout(context) {

    @BindView(R.id.name)
    lateinit var name: TextView

    @BindView(R.id.price)
    lateinit var price: TextView

    init {
        View.inflate(context, R.layout.widget_product_item, this)
        ButterKnife.bind(this)
    }

    fun bind(product: Product) {
        name.text = product.displayName
        val currentPrice = product.currentPrice
        price.text = "${currentPrice.amount} ${currentPrice.currency}"
    }

}