package com.example

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat
import io.mockk.*
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.extension.ExtendWith

import domain.entity.Order
import domain.usecase.`interface`.OrderUseCase
import domain.usecase.`interface`.InventoryUseCase
import domain.usecase.`interface`.SalesUseCase
import presentation.OrderService

/*
    gwt(given-when-then) 방식으로 진행
*/
@ExtendWith(MockKExtension::class)
class OrderServiceTest {
    private lateinit var orderUseCase: OrderUseCase
    private lateinit var inventoryUseCase: InventoryUseCase
    private lateinit var salesUseCase: SalesUseCase
    private lateinit var orderService: OrderService

    @BeforeEach
    fun setUp() {
        orderUseCase = mockk()
        inventoryUseCase = mockk()
        salesUseCase = mockk()
        orderService = OrderService(orderUseCase, inventoryUseCase, salesUseCase)
    }

    @Test
    fun `주문 요청이 성공적으로 처리된다`() {
        // given
        val order = Order("NIKE0001", 3)
        every { orderUseCase.sale(order) } just Runs
        
        // when
        orderService.requestOrder(order)
        
        // then
        verify(exactly = 1) { orderUseCase.sale(order) }
    }

    @Test
    fun `재고 확인이 성공적으로 처리된다`() {
        // given
        val itemId = "NIKE0001"
        val expectedQuantity = 10
        every { inventoryUseCase.checkInventory(itemId) } returns expectedQuantity
        
        // when
        val actualQuantity = orderService.checkInventory(itemId)
        
        // then
        assertThat(actualQuantity).isEqualTo(expectedQuantity)
        verify(exactly = 1) { inventoryUseCase.checkInventory(itemId) }
    }

    @Test
    fun `주문 취소가 성공적으로 처리된다`() {
        // given
        val order = Order("NIKE0001", 3)
        every { orderUseCase.cancel(order) } just Runs
        
        // when
        orderService.cancelOrder(order)
        
        // then
        verify(exactly = 1) { orderUseCase.cancel(order) }
    }

    @Test
    fun `주문 부분 취소가 성공적으로 처리된다`() {
        // given
        val order = Order("NIKE0001", 2)
        every { orderUseCase.partialCancel(order) } just Runs
        
        // when
        orderService.partialCancelOrder(order)
        
        // then
        verify(exactly = 1) { orderUseCase.partialCancel(order) }
    }

    @Test
    fun `특정 상품의 총 판매 금액 조회가 성공적으로 처리된다`() {
        // given
        val itemId = "NIKE0001"
        val expectedAmount = 500000L
        every { salesUseCase.getTotalSalesByItem(itemId) } returns expectedAmount
        
        // when
        val actualAmount = orderService.getTotalSalesByItem(itemId)
        
        // then
        assertThat(actualAmount).isEqualTo(expectedAmount)
        verify(exactly = 1) { salesUseCase.getTotalSalesByItem(itemId) }
    }

    @Test
    fun `전체 판매 금액 조회가 성공적으로 처리된다`() {
        // given
        val expectedSales = mapOf("NIKE0001" to 500000L, "ADIDAS0001" to 300000L)
        every { salesUseCase.getAllSales() } returns expectedSales
        
        // when
        val actualSales = orderService.getAllSales()
        
        // then
        assertThat(actualSales).isEqualTo(expectedSales)
        verify(exactly = 1) { salesUseCase.getAllSales() }
    }
} 