package ru.justd.bux.product

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import ru.justd.arkitec.view.BaseActivity
import ru.justd.bux.R
import ru.justd.bux.app.di.BuxApplication
import ru.justd.bux.app.model.NetworkError
import ru.justd.bux.app.model.ServerError
import ru.justd.bux.app.model.TradingQuote
import ru.justd.bux.app.view.SimpleError
import ru.justd.bux.product.model.Product
import ru.justd.bux.product.presenter.ProductPresenter
import ru.justd.bux.product.view.ProductView
import ru.justd.bux.productslist.view.ProductWidget
import ru.justd.lilwidgets.LilLoaderWidget
import javax.inject.Inject

/**
 * Detailed product view with live time price updates
 */
class ProductActivity : BaseActivity<ProductPresenter, ProductView>(), ProductView {

    companion object {
        const val EXTRA_PRODUCT_ID = "EXTRA_PRODUCT_ID"

        fun start(context: Context, productId: String) {
            val intent = Intent(context, ProductActivity::class.java)
            intent.putExtra(EXTRA_PRODUCT_ID, productId)
            context.startActivity(intent)
        }

    }

    @BindView(R.id.product_widget)
    lateinit var productWidget: ProductWidget

    @BindView(R.id.closing_price)
    lateinit var closingPrice: TextView

    @BindView(R.id.data_container)
    lateinit var dataContainer: View

    @BindView(R.id.loader)
    lateinit var loader: LilLoaderWidget

    @Inject
    lateinit var presenter: ProductPresenter

    override fun presenter() = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        ButterKnife.bind(this)

        BuxApplication.component.inject(this)
    }

    //region ProductView

    override fun getProductId() =
            intent.extras.getString(EXTRA_PRODUCT_ID)
                    ?: throw IllegalArgumentException("ProductActivity must be started via static method providing productId")

    override fun showData(product: Product) {
        loader.hide()
        dataContainer.visibility = View.VISIBLE

        title = product.symbol

        productWidget.bind(product)
        val price = product.closingPrice
        closingPrice.text = "${price.amount} ${price.currency}"
    }

    override fun showLoading() {
        loader.showLoading()
        dataContainer.visibility = View.INVISIBLE
    }

    override fun showError(error: Throwable) {
        when (error) {
            is NetworkError -> {
                loader.showNetworkError()
                loader.setOnErrorClicked { presenter.loadData() }
            }
            is ServerError -> loader.showError(SimpleError(error.message ?: getString(R.string.unexpected_error)))
        }
    }

    override fun updatePrice(quote: TradingQuote) {
        productWidget.updatePrice(quote.currentPrice)
    }

    //endregion
}