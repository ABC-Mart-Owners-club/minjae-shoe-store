package com.example.data

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat
import com.example.domain.Sale
import java.util.UUID

class DefaultSaleRepositoryTest {
    private lateinit var saleRepository: DefaultSaleRepository

    @BeforeEach
    fun setUp() {
        saleRepository = DefaultSaleRepository()
    }

    @Test
    fun `판매 기록이 성공적으로 저장된다`() {
        // given
        val sale = Sale(
            id = UUID.randomUUID().toString(),
            itemId = "NIKE0001",
            quantity = 2,
            price = 100000L,
            totalAmount = 200000L
        )

        // when
        val result = saleRepository.recordSale(sale)

        // then
        assertThat(result).isTrue()
    }

    @Test
    fun `특정 상품의 총 판매 금액이 정확히 계산된다`() {
        // given
        val itemId = "NIKE0001"
        val sale1 = Sale(
            id = UUID.randomUUID().toString(),
            itemId = itemId,
            quantity = 2,
            price = 100000L,
            totalAmount = 200000L
        )
        val sale2 = Sale(
            id = UUID.randomUUID().toString(),
            itemId = itemId,
            quantity = 1,
            price = 100000L,
            totalAmount = 100000L
        )
        saleRepository.recordSale(sale1)
        saleRepository.recordSale(sale2)

        // when
        val totalAmount = saleRepository.getTotalSalesByItem(itemId)

        // then
        assertThat(totalAmount).isEqualTo(300000L)
    }

    @Test
    fun `존재하지 않는 상품의 판매 금액은 0으로 반환된다`() {
        // given
        val nonExistingItemId = "NON_EXISTING"

        // when
        val totalAmount = saleRepository.getTotalSalesByItem(nonExistingItemId)

        // then
        assertThat(totalAmount).isEqualTo(0L)
    }

    @Test
    fun `모든 상품의 판매 금액이 정확히 계산된다`() {
        // given
        val nikeItemId = "NIKE0001"
        val adidasItemId = "ADIDAS0001"
        
        val nikeSale = Sale(
            id = UUID.randomUUID().toString(),
            itemId = nikeItemId,
            quantity = 2,
            price = 100000L,
            totalAmount = 200000L
        )
        
        val adidasSale = Sale(
            id = UUID.randomUUID().toString(),
            itemId = adidasItemId,
            quantity = 1,
            price = 150000L,
            totalAmount = 150000L
        )
        
        saleRepository.recordSale(nikeSale)
        saleRepository.recordSale(adidasSale)

        // when
        val allSales = saleRepository.getAllSales()

        // then
        assertThat(allSales).hasSize(2)
        assertThat(allSales[nikeItemId]).isEqualTo(200000L)
        assertThat(allSales[adidasItemId]).isEqualTo(150000L)
    }
} 