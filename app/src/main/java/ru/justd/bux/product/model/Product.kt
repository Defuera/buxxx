package ru.justd.bux.product.model

data class Product(
        val symbol: String,
        val displayName: String,
        val securityId: String,
        val quoteCurrency: String,
        val displayDecimals: Int,
        val maxLeverage: Int,
        val multiplier: Int,
        val currentPrice: Price,
        val closingPrice: Price,
        val dayRange: PriceRange,
        val yearRange: PriceRange,
        val openingHours : Hours,
        val category : String,
        val favorite : Boolean,
        val productMarketStatus : String,
        val tags : Array<String>
)

data class Price(
        val currency: String,
        val decimals: Int,
        val amount: Double
)

data class PriceRange(
        val currency: String,
        val decimals: Int,
        val high: Double,
        val low: Double
)

data class Hours(
   val timezone : String,
   val weekDays: Array<Array<TimeRange>>
)

data class TimeRange(
        val start : String,
        val end : String
)