package com.example.data.mapper

import com.example.data.model.OrderEntity
import com.example.domain.Order

class OrderMapper {
    fun toDomain(entity: OrderEntity): Order {
        return Order(entity.itemId, entity.quantity)
    }
    
    fun toEntity(domain: Order): OrderEntity {
        return OrderEntity(
            itemId = domain.id,
            quantity = domain.quantity
        )
    }
} 