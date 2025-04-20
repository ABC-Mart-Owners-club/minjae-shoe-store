package com.example

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.DisplayName
import org.assertj.core.api.Assertions.assertThat

import com.example.domain.Item
import com.example.domain.ShoeBrand
import com.example.data.DefaultTradeRepository
import com.example.presentation.OrderService
import com.example.domain.OrderUseCase
import com.example.domain.InventoryUseCase
import com.example.domain.Order
import com.example.data.ShoeStoreStorage

class MainTest {

    @Test
    @DisplayName("주문 성공 테스트")
    fun testOrderItemSuccess() {
        // 1. 주문이 들어온다
        // 2. 재고를 확인한다
        // 3. 재고가 있으면 주문을 처리한다
        val item = Item("NIKE0001", "jordan1", 100000, ShoeBrand.NIKE)
        val initItems = mutableMapOf(
            item.id to 10
        )
        val storage = ShoeStoreStorage(initItems)
        val repository = DefaultTradeRepository(storage)
        val service = OrderService(OrderUseCase(repository), InventoryUseCase(repository))
        val order = Order(item.id, 3)

        service.requestOrder(order)
        assertThat(service.checkInventory(item.id).equals(7))
    }

    @Test
    @DisplayName("재고 부족 테스트")
    fun testOrderItemSoldOutTest() {
        // 1. 주문이 들어온다
        // 2. 재고를 확인한다
        // 3. 재고가 있으면 주문을 처리한다
        // 4. 재고가 없으면 실패 처리를 한다
        val item = Item("NIKE0001", "jordan1", 100000, ShoeBrand.NIKE)
        val initItems = mutableMapOf(
            item.id to 2
        )
        val storage = ShoeStoreStorage(initItems)
        val repository = DefaultTradeRepository(storage)
        val service = OrderService(OrderUseCase(repository), InventoryUseCase(repository))
        val order = Order(item.id, 5)

        service.requestOrder(order)
        assertThat(service.checkInventory(item.id).equals(2))
    }

    @Test
    fun `주문 취소 테스트`() {
        
    }

    @Test
    fun `주문 부분취소 테스트`() {

    }

    @Test
    fun `상품별 판매 금액 조회 테스트`() {

    }

} 