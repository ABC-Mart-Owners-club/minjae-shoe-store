package com.example.data

import com.example.data.mapper.OrderMapper
import com.example.domain.Order
import io.mockk.*
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.assertj.core.api.Assertions.assertThat

@ExtendWith(MockKExtension::class)
class DefaultTradeRepositoryTest {
    
    private lateinit var storage: ShoeStoreStorage
    private lateinit var orderMapper: OrderMapper
    private lateinit var repository: DefaultTradeRepository
    
    @BeforeEach
    fun setUp() {
        storage = mockk()
        orderMapper = mockk()
        repository = DefaultTradeRepository(storage, orderMapper)
    }
    
    @Test
    fun `주문 요청 시 스토리지에 재고 감소 요청이 전달된다`() {
        // given
        val order = Order("NIKE0001", 3)
        val orderEntity = com.example.data.model.OrderEntity(
            itemId = "NIKE0001",
            quantity = 3
        )
        
        every { orderMapper.toEntity(order) } returns orderEntity
        every { storage.substractItem(orderEntity.itemId, orderEntity.quantity) } returns true
        
        // when
        repository.requestOrder(order)
        
        // then
        verify(exactly = 1) { orderMapper.toEntity(order) }
        verify(exactly = 1) { storage.substractItem(orderEntity.itemId, orderEntity.quantity) }
    }
    
    @Test
    fun `주문 취소 시 스토리지에 재고 증가 요청이 전달된다`() {
        // given
        val order = Order("NIKE0001", 3)
        val orderEntity = com.example.data.model.OrderEntity(
            itemId = "NIKE0001",
            quantity = 3
        )
        
        every { orderMapper.toEntity(order) } returns orderEntity
        every { storage.addItem(orderEntity.itemId, orderEntity.quantity) } returns true
        
        // when
        repository.cancelOrder(order)
        
        // then
        verify(exactly = 1) { orderMapper.toEntity(order) }
        verify(exactly = 1) { storage.addItem(orderEntity.itemId, orderEntity.quantity) }
    }
    
    @Test
    fun `재고 확인 시 스토리지에서 정확한 수량을 반환한다`() {
        // given
        val itemId = "NIKE0001"
        val expectedQuantity = 10
        
        every { storage.checkInventory(itemId) } returns expectedQuantity
        
        // when
        val actualQuantity = repository.checkInventory(itemId)
        
        // then
        assertThat(actualQuantity).isEqualTo(expectedQuantity)
        verify(exactly = 1) { storage.checkInventory(itemId) }
    }
} 