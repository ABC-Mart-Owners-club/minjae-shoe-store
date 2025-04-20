package com.example.domain

import java.util.UUID

/**
 * 판매 기록과 조회를 위한 유스케이스 인터페이스
 */
interface SalesUseCase {
    /**
     * 주문을 판매 내역으로 기록합니다.
     *
     * @param order 주문 정보
     * @param price 상품 가격
     * @return 성공 여부
     */
    fun recordSale(order: Order, price: Long): Boolean
    
    /**
     * 특정 상품의 총 판매 금액을 조회합니다.
     *
     * @param itemId 상품 ID
     * @return 총 판매 금액
     */
    fun getTotalSalesByItem(itemId: String): Long
    
    /**
     * 모든 상품의 판매 금액을 조회합니다.
     *
     * @return 상품별 판매 금액 맵 (key: 상품 ID, value: 총 판매 금액)
     */
    fun getAllSales(): Map<String, Long>
}

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