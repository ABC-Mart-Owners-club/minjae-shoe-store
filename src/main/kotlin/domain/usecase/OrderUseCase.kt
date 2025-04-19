package com.example.domain

import com.example.domain.TradeRepository
import com.example.domain.Order

class OrderUseCase(private val tradeRepository: TradeRepository) {
    
    fun requestOrder(order: Order) {    
        tradeRepository.requestOrder(order)
    }

    fun cancelOrder(order: Order) {
        tradeRepository.cancelOrder(order)
    }

    fun partialCancelOrder(order: Order) {
        tradeRepository.partialCancelOrder(order)
    }
}