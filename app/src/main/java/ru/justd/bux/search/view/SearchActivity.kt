package ru.justd.bux.search.view

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import ru.justd.arkitec.view.BaseActivity
import ru.justd.bux.BuxApplication
import ru.justd.bux.R
import ru.justd.bux.Router
import ru.justd.bux.product.model.Product
import ru.justd.bux.search.presenter.SearchPresenter
import javax.inject.Inject

class SearchActivity : BaseActivity<SearchPresenter, SearchView>(), SearchView {

    @Inject
    lateinit var presenter: SearchPresenter

    @Inject
    lateinit var router: Router

    @BindView(R.id.product_input)
    lateinit var input: AutoCompleteTextView

    override fun presenter() = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        BuxApplication.component.inject(this)
    }


    //region SearchView

    override fun setHints(products: List<Product>) {
        input.setAdapter(
                ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_dropdown_item_1line,
                        products.map { it.symbol }.toTypedArray()
                )
        )
    }

    override fun displayDetailedProduct(product: Product) {

    }

    //endregion

    @OnClick(R.id.submit)
    fun onSubmitClicked() {
        router.showDetailedProduct(this, input.text.toString())
    }

}
