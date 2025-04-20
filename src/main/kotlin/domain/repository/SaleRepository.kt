package com.example.domain

/**
 * 판매 내역 관리를 위한 리포지토리 인터페이스
 */
interface SaleRepository {
    /**
     * 판매 내역을 기록합니다.
     * 
     * @param sale 판매 정보
     * @return 등록 성공 여부
     */
    fun recordSale(sale: Sale): Boolean
    
    /**
     * 특정 상품의 총 판매 금액을 조회합니다.
     * 
     * @param itemId 상품 ID
     * @return 총 판매 금액
     */
    fun getTotalSalesByItem(itemId: String): Long
    
    /**
     * 모든 상품의 판매 내역을 조회합니다.
     * 
     * @return 상품별 판매 금액 맵 (key: 상품 ID, value: 총 판매 금액)
     */
    fun getAllSales(): Map<String, Long>
} 