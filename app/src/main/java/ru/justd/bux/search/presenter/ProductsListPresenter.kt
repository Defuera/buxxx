package ru.justd.bux.search.presenter

import ru.justd.arkitec.presenter.BasePresenter
import ru.justd.bux.search.model.SearchInteractor
import ru.justd.bux.search.view.ProductsListView
import rx.functions.Action1
import javax.inject.Inject

class ProductsListPresenter @Inject constructor(
        private val interactor: SearchInteractor
) : BasePresenter<ProductsListView>() {

    override fun onViewAttached() {
        subscribe(
                interactor.getListProducts(),
                Action1 { products -> view().showData(products) },
                Action1 { e -> e.printStackTrace() }
        )

    }

}