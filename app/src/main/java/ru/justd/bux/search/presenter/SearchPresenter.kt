package ru.justd.bux.search.presenter

import android.util.Log
import ru.justd.arkitec.presenter.BasePresenter
import ru.justd.bux.search.model.SearchInteractor
import ru.justd.bux.search.view.SearchView
import rx.functions.Action1
import javax.inject.Inject

class SearchPresenter @Inject constructor(
        private val interactor: SearchInteractor
) : BasePresenter<SearchView>() {

    override fun onViewAttached() {
        subscribe(
                interactor.getListProducts(),
                Action1 { products -> products.forEach { Log.i("DensTest", "$products"); } },
                Action1 { e -> e.printStackTrace() }
        )
    }

}