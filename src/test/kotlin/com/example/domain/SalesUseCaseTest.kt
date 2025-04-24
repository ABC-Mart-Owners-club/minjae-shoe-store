package com.example.domain

import domain.entity.Order
import domain.repository.SaleRepository
import domain.usecase.DefaultSalesUseCase
import io.mockk.*
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.assertj.core.api.Assertions.assertThat

@ExtendWith(MockKExtension::class)
class SalesUseCaseTest {
    private lateinit var saleRepository: SaleRepository
    private lateinit var salesUseCase: DefaultSalesUseCase

    @BeforeEach
    fun setUp() {
        saleRepository = mockk()
        salesUseCase = DefaultSalesUseCase(saleRepository)
    }

    @Test
    fun `판매 기록이 성공적으로 등록된다`() {
        // given
        val order = Order("NIKE0001", 2)
        val price = 100000L
        val expectedTotal = order.quantity * price
        every { saleRepository.recordSale(any()) } returns true

        // when
        val result = salesUseCase.recordSale(order, price)

        // then
        assertThat(result).isTrue()
        verify { 
            saleRepository.recordSale(match { sale ->
                sale.itemId == order.id &&
                sale.quantity == order.quantity &&
                sale.price == price &&
                sale.totalAmount == expectedTotal
            })
        }
    }

    @Test
    fun `특정 상품의 총 판매 금액을 조회한다`() {
        // given
        val itemId = "NIKE0001"
        val expectedAmount = 200000L
        every { saleRepository.getTotalSalesByItem(itemId) } returns expectedAmount

        // when
        val result = salesUseCase.getTotalSalesByItem(itemId)

        // then
        assertThat(result).isEqualTo(expectedAmount)
        verify(exactly = 1) { saleRepository.getTotalSalesByItem(itemId) }
    }

    @Test
    fun `모든 상품의 판매 금액을 조회한다`() {
        // given
        val expectedSales = mapOf(
            "NIKE0001" to 200000L,
            "ADIDAS0001" to 150000L
        )
        every { saleRepository.getAllSales() } returns expectedSales

        // when
        val result = salesUseCase.getAllSales()

        // then
        assertThat(result).isEqualTo(expectedSales)
        verify(exactly = 1) { saleRepository.getAllSales() }
    }
} 