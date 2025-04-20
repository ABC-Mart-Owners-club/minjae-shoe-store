package com.example.data

import com.example.data.ShoeStoreStorage
import com.example.domain.Order
import com.example.domain.TradeRepository

public class DefaultTradeRepository(inventory: ShoeStoreStorage) : TradeRepository {
    private val inventory: ShoeStoreStorage = inventory

    override fun requestOrder(order: Order) {
        inventory.substractItem(order.id, order.quantity)
    }

    override fun cancelOrder(order: Order) {
        inventory.addItem(order.id, order.quantity)
    }

    override fun partialCancelOrder(order: Order) {
        inventory.addItem(order.id, order.quantity)
    }

    public override fun checkInventory(id: String): Int {
        return inventory.checkInventory(id)
    }
}
