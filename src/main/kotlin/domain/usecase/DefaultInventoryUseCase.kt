package com.example.domain

import domain.repository.TradeRepository
import domain.usecase.`interface`.InventoryUseCase

class DefaultInventoryUseCase(private val tradeRepository: TradeRepository) : InventoryUseCase {
    override fun checkInventory(id: String): Int {
        return tradeRepository.checkInventory(id)
    }
}