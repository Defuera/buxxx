package ru.justd.bux.search.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ru.justd.bux.R

class SearchActivity : AppCompatActivity() { //BaseActivity<SearchPresenter, SearchView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
