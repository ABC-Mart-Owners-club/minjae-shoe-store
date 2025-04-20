package com.example.data

import com.example.data.ShoeStoreStorage
import com.example.data.mapper.OrderMapper
import com.example.domain.Order
import com.example.domain.TradeRepository

class DefaultTradeRepository(
    private val storage: ShoeStoreStorage,
    private val orderMapper: OrderMapper = OrderMapper()
) : TradeRepository {

    override fun saledItem(order: Order) {
        val orderEntity = orderMapper.toEntity(order)
        storage.substractItem(orderEntity.itemId, orderEntity.quantity)
    }

    override fun canceledItem(order: Order) {
        val orderEntity = orderMapper.toEntity(order)
        storage.addItem(orderEntity.itemId, orderEntity.quantity)
    }

    override fun partialCancelOrder(order: Order) {
        val orderEntity = orderMapper.toEntity(order)
        storage.addItem(orderEntity.itemId, orderEntity.quantity)
    }

    override fun checkInventory(id: String): Int {
        return storage.checkInventory(id)
    }
}
