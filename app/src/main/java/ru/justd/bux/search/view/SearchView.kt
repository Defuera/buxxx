package ru.justd.bux.search.view

import ru.justd.bux.product.model.Product

interface SearchView {

    fun setHints(products: List<Product>)

    fun displayDetailedProduct(product: Product)

}