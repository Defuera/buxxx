package ru.justd.bux.product.model

data class Product(
        val symbol: String,
        val displayName: String,
        val securityId: String,
        val currentPrice: Price,
        val closingPrice: Price
)

data class Price(
        val currency: String,
        val decimals: Int,
        val amount: Double
)