package com.example.domain

import com.example.domain.TradeRepository

interface InventoryUseCase {
    fun checkInventory(id: String): Int
}

class DefaultInventoryUseCase(private val tradeRepository: TradeRepository) : InventoryUseCase {
    override fun checkInventory(id: String): Int {
        return tradeRepository.checkInventory(id)
    }
}