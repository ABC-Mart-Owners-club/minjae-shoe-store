package domain.entity

import java.time.LocalDateTime

/**
 * 상품 판매 정보를 담는 엔티티
 * 
 * @property id 판매 고유 ID
 * @property itemId 판매된 상품 ID
 * @property quantity 판매 수량
 * @property price 판매 가격 (단가)
 * @property totalAmount 총 판매 금액 (수량 * 가격)
 * @property timestamp 판매 시간
 */
data class Sale(
    val id: String,
    val itemId: String,
    val quantity: Int,
    val price: Long,
    val totalAmount: Long = quantity * price,
    val timestamp: LocalDateTime = LocalDateTime.now()
)