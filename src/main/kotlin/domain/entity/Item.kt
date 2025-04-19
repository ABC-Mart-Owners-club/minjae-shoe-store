package com.example.domain

class Item (id: String, name: String, price: Long, brand: ShoeBrand) {
    val id: String = id
    var name: String = name
        get() {
            return field
        }
        set(value) {
            field = value
        }
    val price: Long = price
        get() {
            return field
        }
    val brand: ShoeBrand = brand
}

enum class ShoeBrand {
    NIKE,
    ADIDAS,
    PUMA,
    NEWBALANCE,
    ASICS,
    CONVERSE,
    VANS
}