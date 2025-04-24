package data.repository

import data.ShoeStoreStorage
import data.mapper.OrderMapper
import domain.entity.Order
import domain.repository.TradeRepository

class DefaultTradeRepository(
    private val storage: ShoeStoreStorage,
    private val orderMapper: OrderMapper = OrderMapper()
) : TradeRepository {

    override fun sale(order: Order) {
        val orderEntity = orderMapper.toEntity(order)
        storage.subtractItem(orderEntity.itemId, orderEntity.quantity)
    }

    override fun cancel(order: Order) {
        val orderEntity = orderMapper.toEntity(order)
        storage.addItem(orderEntity.itemId, orderEntity.quantity)
    }

    override fun partialCancel(order: Order) {
        val orderEntity = orderMapper.toEntity(order)
        storage.addItem(orderEntity.itemId, orderEntity.quantity)
    }

    override fun checkInventory(id: String): Int {
        return storage.checkInventory(id)
    }
}
