package ru.justd.bux.productslist.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import butterknife.BindColor
import butterknife.BindView
import butterknife.ButterKnife
import ru.justd.bux.R
import ru.justd.bux.product.model.Product
import java.math.BigDecimal

private const val TREND_DECIMAL_PLACES = 2

class ProductWidget(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    constructor(context: Context) : this(context, null)

    @BindView(R.id.name)
    lateinit var name: TextView

    @BindView(R.id.price)
    lateinit var price: TextView

    @BindView(R.id.trend)
    lateinit var trend: TextView

    @BindColor(R.color.trend_up)
    @JvmField
    var trendUpColor: Int = 0

    @BindColor(R.color.trend_down)
    @JvmField
    var trendDownColor: Int = 0

    @BindColor(R.color.black)
    @JvmField
    var neutralColor: Int = 0

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
        val currentPrice = product.currentPrice
        this.price.text = "${round(currentPrice.decimals, price)} ${currentPrice.currency}"
        val trendPercent = round(TREND_DECIMAL_PLACES, price / product.closingPrice.amount - 1)

        trend.setTextColor(
                when {
                    trendPercent > 0 -> trendUpColor
                    trendPercent < 0 -> trendDownColor
                    else -> neutralColor
                }
        )
        trend.text = "$trendPercent%"
    }

    private fun round(decimalPlaces: Int, num: Double) =
            BigDecimal(num).setScale(decimalPlaces, BigDecimal.ROUND_HALF_DOWN).toDouble()

}