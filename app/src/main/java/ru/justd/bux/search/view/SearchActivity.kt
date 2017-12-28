package ru.justd.bux.search.view

import android.os.Bundle
import ru.justd.arkitec.view.BaseActivity
import ru.justd.bux.BuxApplication
import ru.justd.bux.R
import ru.justd.bux.search.presenter.SearchPresenter
import javax.inject.Inject

class SearchActivity : BaseActivity<SearchPresenter, SearchView>() {

    @Inject
    lateinit var searchPresenter: SearchPresenter

    override fun presenter() = searchPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BuxApplication.component.inject(this)
    }

}
