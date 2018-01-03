package ru.justd.bux.productslist.view

import ru.justd.bux.product.model.Product

interface ProductsListView {

    fun showLoading()

    fun showData(products: List<Product>)

    fun showError(error: Throwable)

}