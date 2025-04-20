package com.example.presentation

import com.example.domain.Order
import com.example.domain.OrderUseCase
import com.example.domain.InventoryUseCase
import com.example.domain.SalesUseCase

class OrderService(
    private val orderUseCase: OrderUseCase,
    private val inventoryUseCase: InventoryUseCase,
    private val salesUseCase: SalesUseCase
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
    
    fun getTotalSalesByItem(itemId: String): Long {
        return salesUseCase.getTotalSalesByItem(itemId)
    }
    
    fun getAllSales(): Map<String, Long> {
        return salesUseCase.getAllSales()
    }
}