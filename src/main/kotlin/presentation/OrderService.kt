package com.example.presentation

import com.example.domain.Order
import com.example.domain.OrderUseCase
import com.example.domain.InventoryUseCase

class OrderService(
    val orderUseCase: OrderUseCase,
    val inventoryUseCase: InventoryUseCase
    ) {

    fun requestOrder(order: Order) {
        orderUseCase.requestOrder(order)
    }

    fun checkInventory(id: String): Int {
        return inventoryUseCase.checkInventory(id)
    }

    fun cancelOrder(order: Order) {
        orderUseCase.cancelOrder(order)
    }

    fun partialCancelOrder(order: Order) {
        orderUseCase.partialCancelOrder(order)
    }
}