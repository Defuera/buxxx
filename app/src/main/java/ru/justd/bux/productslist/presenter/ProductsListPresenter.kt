package ru.justd.bux.productslist.presenter

import ru.justd.arkitec.presenter.BasePresenter
import ru.justd.bux.productslist.model.ProductsListInteractor
import ru.justd.bux.productslist.view.ProductsListView
import rx.functions.Action1
import javax.inject.Inject

class ProductsListPresenter @Inject constructor(
        private val interactor: ProductsListInteractor
) : BasePresenter<ProductsListView>() {

    override fun onViewAttached() {
        loadData()
    }

    fun loadData() {
        view().showLoading()
        subscribe(
                interactor.getListProducts(),
                Action1 { products -> view().showData(products) },
                Action1 { view().showError(it) }
        )
    }

}