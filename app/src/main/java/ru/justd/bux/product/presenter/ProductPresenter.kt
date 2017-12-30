package ru.justd.bux.product.presenter

import ru.justd.arkitec.presenter.BasePresenter
import ru.justd.bux.product.model.Product
import ru.justd.bux.product.model.ProductInteractor
import ru.justd.bux.product.view.ProductView
import rx.functions.Action1
import javax.inject.Inject

class ProductPresenter @Inject constructor(
        private val interactor: ProductInteractor
): BasePresenter<ProductView>() {

    override fun onViewAttached() {
        subscribe(
                interactor.getProduct(view().getProductId()),
                onSuccess = Action1 { product ->
                    view().showData(product)
                    observeProductUpdates(product)
                },
                onError = Action1 { throwable -> view().showError(throwable) }
        )
    }

    private fun observeProductUpdates(product: Product) {
        //        subscribe(
//                interactor.observeProduct(view().getProductId())
//        )
    }

}