package com.example.domain

import com.example.domain.TradeRepository
import com.example.domain.Order

interface OrderUseCase {
    fun requestOrder(order: Order)
    fun cancelOrder(order: Order)
    fun partialCancelOrder(order: Order)
}

class DefaultOrderUseCase(private val tradeRepository: TradeRepository) : OrderUseCase {
    
    override fun requestOrder(order: Order) {    
        tradeRepository.saledItem(order)
    }

    override fun cancelOrder(order: Order) {
        tradeRepository.canceledItem(order)
    }

    override fun partialCancelOrder(order: Order) {
        tradeRepository.partialCancelOrder(order)
    }
}