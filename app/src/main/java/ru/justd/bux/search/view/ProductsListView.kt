package ru.justd.bux.search.view

import ru.justd.bux.product.model.Product

interface ProductsListView {

    fun showData(products: List<Product>)

}