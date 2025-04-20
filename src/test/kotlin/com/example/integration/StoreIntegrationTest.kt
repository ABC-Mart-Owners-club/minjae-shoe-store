package com.example.integration

import com.example.data.DefaultSaleRepository
import com.example.data.DefaultTradeRepository
import com.example.data.ShoeStoreStorage
import com.example.data.mapper.OrderMapper
import com.example.domain.DefaultInventoryUseCase
import com.example.domain.DefaultOrderUseCase
import com.example.domain.DefaultSalesUseCase
import com.example.domain.Item
import com.example.domain.Order
import com.example.domain.ShoeBrand
import com.example.presentation.OrderService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested

/**
 * 매장 주문과 판매 기능에 대한 통합 테스트
 */
class StoreIntegrationTest {
    
    private lateinit var storage: ShoeStoreStorage
    private lateinit var repository: DefaultTradeRepository
    private lateinit var saleRepository: DefaultSaleRepository
    private lateinit var orderUseCase: DefaultOrderUseCase
    private lateinit var inventoryUseCase: DefaultInventoryUseCase
    private lateinit var salesUseCase: DefaultSalesUseCase
    private lateinit var orderService: OrderService
    
    private val nikeShoe = Item("NIKE0001", "Air Jordan 1", 150000, ShoeBrand.NIKE)
    private val adidasShoe = Item("ADIDAS0001", "Superstar", 120000, ShoeBrand.ADIDAS)
    
    @BeforeEach
    fun setUp() {
        // 초기 재고 설정
        val initialInventory = mutableMapOf(
            nikeShoe.id to 10,
            adidasShoe.id to 5
        )
        
        storage = ShoeStoreStorage(initialInventory)
        repository = DefaultTradeRepository(storage, OrderMapper())
        saleRepository = DefaultSaleRepository()
        orderUseCase = DefaultOrderUseCase(repository)
        inventoryUseCase = DefaultInventoryUseCase(repository)
        salesUseCase = DefaultSalesUseCase(saleRepository)
        orderService = OrderService(orderUseCase, inventoryUseCase, salesUseCase)
    }

    @Nested
    inner class OrderTests {
        @Test
        fun `주문 후 재고가 정확히 감소한다`() {
            // given
            val order = Order(nikeShoe.id, 3)
            val initialQuantity = orderService.checkInventory(nikeShoe.id)
            
            // when
            orderService.requestOrder(order)
            
            // then
            val finalQuantity = orderService.checkInventory(nikeShoe.id)
            assertThat(finalQuantity).isEqualTo(initialQuantity - order.quantity)
            assertThat(finalQuantity).isEqualTo(7) // 초기값 10에서 3개 감소
        }
        
        @Test
        fun `주문 취소 후 재고가 정확히 증가한다`() {
            // given
            val order = Order(adidasShoe.id, 2)
            orderService.requestOrder(order) // 먼저 주문
            val quantityAfterOrder = orderService.checkInventory(adidasShoe.id)
            
            // when
            orderService.cancelOrder(order) // 주문 취소
            
            // then
            val finalQuantity = orderService.checkInventory(adidasShoe.id)
            assertThat(finalQuantity).isEqualTo(quantityAfterOrder + order.quantity)
            assertThat(finalQuantity).isEqualTo(5) // 초기값 5에서 2개 감소 후 2개 증가
        }
        
        @Test
        fun `주문 수량이 재고보다 많을 경우 재고는 변하지 않는다`() {
            // given
            val excessiveOrder = Order(adidasShoe.id, 10) // 재고(5)보다 많은 수량
            val initialQuantity = orderService.checkInventory(adidasShoe.id)
            
            // when
            orderService.requestOrder(excessiveOrder)
            
            // then
            val finalQuantity = orderService.checkInventory(adidasShoe.id)
            assertThat(finalQuantity).isEqualTo(initialQuantity) // 재고에 변화가 없어야 함
            assertThat(finalQuantity).isEqualTo(5)
        }
        
        @Test
        fun `부분 취소 후 재고가 정확히 증가한다`() {
            // given
            val order = Order(nikeShoe.id, 4)
            orderService.requestOrder(order) // 먼저 주문
            val quantityAfterOrder = orderService.checkInventory(nikeShoe.id)
            
            // when
            val partialCancelOrder = Order(nikeShoe.id, 2) // 부분 취소
            orderService.partialCancelOrder(partialCancelOrder)
            
            // then
            val finalQuantity = orderService.checkInventory(nikeShoe.id)
            assertThat(finalQuantity).isEqualTo(quantityAfterOrder + partialCancelOrder.quantity)
            assertThat(finalQuantity).isEqualTo(8) // 초기값 10에서 4개 감소 후 2개 증가
        }
    }

    @Nested
    inner class SalesTests {
        /**
         * 판매 기록을 생성하고 테스트를 위한 판매 내역 데이터를 준비합니다.
         */
        private fun prepareSalesData() {
            // 나이키 신발 3개 판매
            val nikeOrder = Order(nikeShoe.id, 3)
            salesUseCase.recordSale(nikeOrder, nikeShoe.price)
            orderService.requestOrder(nikeOrder)
            
            // 아디다스 신발 2개 판매
            val adidasOrder = Order(adidasShoe.id, 2)
            salesUseCase.recordSale(adidasOrder, adidasShoe.price)
            orderService.requestOrder(adidasOrder)
            
            // 추가 나이키 신발 2개 판매
            val additionalNikeOrder = Order(nikeShoe.id, 2)
            salesUseCase.recordSale(additionalNikeOrder, nikeShoe.price)
            orderService.requestOrder(additionalNikeOrder)
        }
        
        @Test
        fun `특정 상품의 총 판매 금액을 정확히 조회한다`() {
            // given
            prepareSalesData()
            
            // when
            val nikeTotalSales = orderService.getTotalSalesByItem(nikeShoe.id)
            
            // then
            val expectedNikeSales = 5 * nikeShoe.price
            assertThat(nikeTotalSales).isEqualTo(expectedNikeSales)
            assertThat(nikeTotalSales).isEqualTo(750000)
        }
        
        @Test
        fun `모든 상품의 판매 금액을 정확히 조회한다`() {
            // given
            prepareSalesData()
            
            // when
            val allSales = orderService.getAllSales()
            
            // then
            assertThat(allSales).containsKey(nikeShoe.id)
            assertThat(allSales).containsKey(adidasShoe.id)
            
            assertThat(allSales[nikeShoe.id]).isEqualTo(750000)
            assertThat(allSales[adidasShoe.id]).isEqualTo(240000)
        }
        
        @Test
        fun `판매 기록이 없는 상품의 조회 결과는 0이다`() {
            // given
            prepareSalesData()
            val unknownItemId = "UNKNOWN001"
            
            // when
            val totalSales = orderService.getTotalSalesByItem(unknownItemId)
            
            // then
            assertThat(totalSales).isEqualTo(0)
        }
    }
} 