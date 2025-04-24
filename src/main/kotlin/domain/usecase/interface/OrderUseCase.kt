package domain.usecase.`interface`

import domain.entity.Order

interface OrderUseCase {
    fun sale(order: Order)
    fun cancel(order: Order)
    fun partialCancel(order: Order)
}