package presentation

import domain.usecase.`interface`.OrderUseCase
import domain.entity.Order
import domain.usecase.`interface`.InventoryUseCase
import domain.usecase.`interface`.SalesUseCase

class OrderService(
    private val orderUseCase: OrderUseCase,
    private val inventoryUseCase: InventoryUseCase,
    private val salesUseCase: SalesUseCase
) {

    fun requestOrder(order: Order) {
        orderUseCase.sale(order)
    }

    fun checkInventory(id: String): Int {
        return inventoryUseCase.checkInventory(id)
    }

    fun cancelOrder(order: Order) {
        orderUseCase.cancel(order)
    }

    fun partialCancelOrder(order: Order) {
        orderUseCase.partialCancel(order)
    }
    
    fun getTotalSalesByItem(itemId: String): Long {
        return salesUseCase.getTotalSalesByItem(itemId)
    }
    
    fun getAllSales(): Map<String, Long> {
        return salesUseCase.getAllSales()
    }
}