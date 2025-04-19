package com.example

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.DisplayName
import org.assertj.core.api.Assertions.assertThat
import io.mockk.*
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.extension.ExtendWith

import com.example.domain.Order
import com.example.domain.OrderUseCase
import com.example.domain.InventoryUseCase
import com.example.presentation.OrderService

/*
    gwt(given-when-then) 방식으로 진행
*/
@ExtendWith(MockKExtension::class)
class OrderServiceTest {
    private lateinit var orderUseCase: OrderUseCase
    private lateinit var inventoryUseCase: InventoryUseCase
    private lateinit var orderService: OrderService

    @BeforeEach
    fun setUp() {
        orderUseCase = mockk()
        inventoryUseCase = mockk()
        orderService = OrderService(orderUseCase, inventoryUseCase)
    }

    @Test
    @DisplayName("주문 요청 테스트")
    fun `주문 요청이 성공적으로 처리된다`() {
        // given
        val order = Order("NIKE0001", 3)
        every { orderUseCase.requestOrder(order) } just Runs
        
        // when
        orderService.requestOrder(order)
        
        // then
        verify(exactly = 1) { orderUseCase.requestOrder(order) }
    }

    @Test
    @DisplayName("재고 확인 테스트")
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
    @DisplayName("주문 취소 테스트")
    fun `주문 취소가 성공적으로 처리된다`() {
        // given
        val order = Order("NIKE0001", 3)
        every { orderUseCase.cancelOrder(order) } just Runs
        
        // when
        orderService.cancelOrder(order)
        
        // then
        verify(exactly = 1) { orderUseCase.cancelOrder(order) }
    }

    @Test
    @DisplayName("주문 부분 취소 테스트")
    fun `주문 부분 취소가 성공적으로 처리된다`() {
        // given
        val order = Order("NIKE0001", 2)
        every { orderUseCase.partialCancelOrder(order) } just Runs
        
        // when
        orderService.partialCancelOrder(order)
        
        // then
        verify(exactly = 1) { orderUseCase.partialCancelOrder(order) }
    }
} 