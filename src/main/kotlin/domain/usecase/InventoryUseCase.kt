package com.example.domain

import com.example.domain.TradeRepository

class InventoryUseCase(private val tradeRepository: TradeRepository) {
    fun checkInventory(id: String): Int {
        return tradeRepository.checkInventory(id)
    }
}