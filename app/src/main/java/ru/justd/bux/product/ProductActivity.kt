package ru.justd.bux.product

import android.content.Context
import android.content.Intent
import android.os.Bundle
import butterknife.ButterKnife
import ru.justd.arkitec.view.BaseActivity
import ru.justd.bux.BuxApplication
import ru.justd.bux.R
import ru.justd.bux.product.presenter.ProductPresenter
import ru.justd.bux.product.view.ProductView
import javax.inject.Inject

class ProductActivity : BaseActivity<ProductPresenter, ProductView>(), ProductView {

    companion object {
        const val EXTRA_PRODUCT_ID = "EXTRA_PRODUCT_ID"

        fun start(context: Context, productId: String) {
            val intent = Intent(context, ProductActivity::class.java)
            intent.putExtra(EXTRA_PRODUCT_ID, productId)
            context.startActivity(intent)
        }

    }

    @Inject
    lateinit var presenter: ProductPresenter

    override fun presenter() = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        ButterKnife.bind(this)

        BuxApplication.component.inject(this)
    }

//    In a second screen, show the data of the Product with the provided Identifier.
//    To show current price of the Product, please take into account the right decimal digits and locale-aware currency formatting.
//    Besides the value of the previous day closing price, please also show the difference of the previous day closing price to the
//    current price, in %. (e.g. if previous day = $100,00 and current = $150,00, the % difference will be 50%).

    //region ProductView

    override fun getProductId() =
            intent.extras.getString(EXTRA_PRODUCT_ID)
                    ?: throw IllegalArgumentException("ProductActivity must be started via static method providing productId")


    //endregion
}