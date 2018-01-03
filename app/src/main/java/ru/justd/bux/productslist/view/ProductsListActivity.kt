package ru.justd.bux.productslist.view

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import butterknife.BindView
import butterknife.ButterKnife
import ru.justd.arkitec.view.BaseActivity
import ru.justd.bux.R
import ru.justd.bux.app.Router
import ru.justd.bux.app.di.BuxApplication
import ru.justd.bux.app.model.NetworkError
import ru.justd.bux.app.model.ServerError
import ru.justd.bux.app.view.SimpleError
import ru.justd.bux.product.model.Product
import ru.justd.bux.productslist.presenter.ProductsListPresenter
import ru.justd.duperadapter.ArrayListDuperAdapter
import ru.justd.lilwidgets.LilLoaderWidget
import javax.inject.Inject

class ProductsListActivity : BaseActivity<ProductsListPresenter, ProductsListView>(), ProductsListView {

    @Inject
    lateinit var presenter: ProductsListPresenter

    @Inject
    lateinit var router: Router

    @BindView(R.id.recycler)
    lateinit var recycler: RecyclerView

    @BindView(R.id.loader)
    lateinit var loader: LilLoaderWidget

    private val adapter = ArrayListDuperAdapter()

    override fun presenter() = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        BuxApplication.component.inject(this)

        recycler.layoutManager = LinearLayoutManager(this)

        adapter
                .addViewType<Product, ProductWidget>(Product::class.java)
                .addViewCreator { viewGroup ->
                    val productWidget = ProductWidget(viewGroup.context)
                    productWidget.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                    productWidget
                }
                .addViewBinder { widget, item -> widget.bind(item) }
                .addClickListener { _, item -> router.showDetailedProduct(this, item.securityId) }
                .commit()

        recycler.adapter = adapter
    }


    //region ProductsListView

    override fun showLoading() {
        recycler.visibility = View.INVISIBLE
        loader.showLoading()
    }

    override fun showData(products: List<Product>) {
        loader.hide()
        recycler.visibility = View.VISIBLE

        adapter.addAll(products)
        adapter.notifyDataSetChanged()
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

    //endregion

}
