package ru.justd.bux.app.di

import dagger.Component
import ru.justd.bux.product.ProductActivity
import ru.justd.bux.productslist.view.ProductsListActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [BuxModule::class, ApiModule::class])
interface BuxComponent {
    fun inject(mainActivity: ProductsListActivity)
    fun inject(mainActivity: ProductActivity)
    fun inject(application: BuxApplication)
}
