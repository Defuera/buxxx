package ru.justd.bux.product.presenter

import ru.justd.arkitec.presenter.BasePresenter
import ru.justd.bux.product.model.ProductInteractor
import ru.justd.bux.product.view.ProductView
import rx.functions.Action1
import javax.inject.Inject

class ProductPresenter @Inject constructor(
        private val interactor: ProductInteractor
) : BasePresenter<ProductView>() {

    override fun onViewAttached() {
        loadData()
    }

    fun loadData() {
        view().showLoading()
        subscribe(
                interactor.getProduct(view().getProductId()),
                onSuccess = Action1 { product ->
                    view().showData(product)
                    subscribeToUpdates(product.securityId)
                },
                onError = Action1 { throwable -> view().showError(throwable) }
        )
    }

    private fun subscribeToUpdates(productId: String) {
        subscribe(
                interactor.subscribeToUpdates(productId),
                onNext = Action1 { quote -> view().updatePrice(quote) },
                onError = Action1 { throwable -> throwable.printStackTrace() }
        )
    }

    override fun detachView() {
        interactor.unsubscribeFromUpdates(view().getProductId())
        super.detachView()
    }

}