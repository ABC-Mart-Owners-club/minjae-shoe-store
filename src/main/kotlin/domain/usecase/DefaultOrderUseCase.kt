package domain.usecase

import domain.entity.Order
import domain.usecase.`interface`.OrderUseCase
import domain.repository.TradeRepository

class DefaultOrderUseCase(private val tradeRepository: TradeRepository) : OrderUseCase {
    
    override fun sale(order: Order) {
        tradeRepository.sale(order)
    }

    override fun cancel(order: Order) {
        tradeRepository.cancel(order)
    }

    override fun partialCancel(order: Order) {
        tradeRepository.partialCancel(order)
    }
}