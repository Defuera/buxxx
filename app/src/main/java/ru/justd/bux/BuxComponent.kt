package ru.justd.bux

import dagger.Component
import ru.justd.bux.product.ProductActivity
import ru.justd.bux.search.view.ProductsListActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [BuxModule::class, ApiModule::class])
interface BuxComponent {
    fun inject(mainActivity: ProductsListActivity)
    fun inject(mainActivity: ProductActivity)
}
