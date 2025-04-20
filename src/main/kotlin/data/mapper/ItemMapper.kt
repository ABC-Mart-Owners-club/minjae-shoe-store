package com.example.data.mapper

import com.example.data.model.ItemEntity
import com.example.domain.Item
import com.example.domain.ShoeBrand

class ItemMapper {
    fun toDomain(entity: ItemEntity): Item {
        val brand = when (entity.brandCode) {
            "NIKE" -> ShoeBrand.NIKE
            "ADIDAS" -> ShoeBrand.ADIDAS
            "PUMA" -> ShoeBrand.PUMA
            "NEWBALANCE" -> ShoeBrand.NEWBALANCE
            "ASICS" -> ShoeBrand.ASICS
            "CONVERSE" -> ShoeBrand.CONVERSE
            "VANS" -> ShoeBrand.VANS
            else -> throw IllegalArgumentException("Unknown brand code: ${entity.brandCode}")
        }
        
        return Item(entity.id, entity.name, entity.price, brand)
    }
    
    fun toEntity(domain: Item, stock: Int = 0): ItemEntity {
        return ItemEntity(
            id = domain.id,
            name = domain.name,
            price = domain.price,
            brandCode = domain.brand.name,
            stock = stock
        )
    }
} 