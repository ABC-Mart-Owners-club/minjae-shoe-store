package com.example.data.mapper

import com.example.data.model.SaleEntity
import com.example.domain.Sale
import java.util.UUID

class SaleMapper {
    fun toEntity(domain: Sale): SaleEntity {
        return SaleEntity(
            id = domain.id,
            itemId = domain.itemId,
            quantity = domain.quantity,
            price = domain.price,
            totalAmount = domain.totalAmount,
            timestamp = domain.timestamp
        )
    }
    
    fun toDomain(entity: SaleEntity): Sale {
        return Sale(
            id = entity.id,
            itemId = entity.itemId,
            quantity = entity.quantity,
            price = entity.price,
            totalAmount = entity.totalAmount,
            timestamp = entity.timestamp
        )
    }
} 