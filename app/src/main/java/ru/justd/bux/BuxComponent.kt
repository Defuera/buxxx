package ru.justd.fundaassignment

import dagger.Component
import ru.justd.bux.search.view.SearchActivity
import javax.inject.Singleton

/**
 * Created by defuera on 05/02/2017.
 */
@Singleton
@Component(modules = [(ApiModule::class)])
interface BuxComponent {
    fun inject(mainActivity: SearchActivity)
}
