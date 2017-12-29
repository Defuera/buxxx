package ru.justd.bux.product.presenter

import ru.justd.arkitec.presenter.BasePresenter
import ru.justd.bux.product.model.ProductInteractor
import ru.justd.bux.product.view.ProductView
import javax.inject.Inject

class ProductPresenter @Inject constructor(
        private val interactor: ProductInteractor
): BasePresenter<ProductView>() {

    override fun onViewAttached() {
        subscribe(
                interactor.observeProduct(view().getProductId())
        )
//        subscribe(
//                interactor.getProduct(view().getProductId())
//        )
    }

}