package domain.repository

import domain.entity.Order

interface TradeRepository {
    fun sale(order: Order)
    fun cancel(order: Order)
    fun partialCancel(order: Order)
    fun checkInventory(id: String): Int
}