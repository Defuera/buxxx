package ru.justd.bux.productslist.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import ru.justd.bux.R
import ru.justd.bux.product.model.Product
import java.math.BigDecimal

class ProductWidget(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    constructor(context: Context) : this(context, null)

    @BindView(R.id.name)
    lateinit var name: TextView

    @BindView(R.id.price)
    lateinit var price: TextView

    @BindView(R.id.trend)
    lateinit var trend: TextView

    lateinit var product: Product

    init {
        View.inflate(context, R.layout.widget_product_item, this)
        ButterKnife.bind(this, this)
    }

    fun bind(product: Product) {
        this.product = product
        name.text = product.displayName
        updatePrice(product.currentPrice.amount)
    }

    fun updatePrice(price: Double) {
        this.price.text = "$price ${product.currentPrice.currency}"
        trend.text = "${round(price / product.closingPrice.amount - 1)}%"
    }

    private fun round(num: Double) =
            BigDecimal(num).setScale(2, BigDecimal.ROUND_HALF_DOWN).toDouble()

}