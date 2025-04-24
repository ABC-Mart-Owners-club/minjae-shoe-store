package data.mapper

import data.model.OrderEntity
import domain.entity.Order

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