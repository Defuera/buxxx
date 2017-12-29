package ru.justd.bux

import dagger.Component
import ru.justd.bux.search.view.SearchActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [BuxModule::class, ApiModule::class])
interface BuxComponent {
    fun inject(mainActivity: SearchActivity)
}
