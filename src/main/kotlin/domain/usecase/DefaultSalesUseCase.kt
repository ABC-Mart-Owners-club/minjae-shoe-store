package domain.usecase

import domain.entity.Order
import domain.entity.Sale
import domain.repository.SaleRepository
import domain.usecase.`interface`.SalesUseCase
import java.util.UUID

/**
 * 판매 내역 관리 유스케이스 구현체
 */
class DefaultSalesUseCase(private val saleRepository: SaleRepository) : SalesUseCase {
    
    override fun recordSale(order: Order, price: Long): Boolean {
        val sale = Sale(
            id = UUID.randomUUID().toString(),
            itemId = order.id,
            quantity = order.quantity,
            price = price,
            totalAmount = order.quantity * price
        )
        return saleRepository.recordSale(sale)
    }
    
    override fun getTotalSalesByItem(itemId: String): Long {
        return saleRepository.getTotalSalesByItem(itemId)
    }

    override fun getAllSales(): Map<String, Long> {
        return saleRepository.getAllSales()
    }
} 