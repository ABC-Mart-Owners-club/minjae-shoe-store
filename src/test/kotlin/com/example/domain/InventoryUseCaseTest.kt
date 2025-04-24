package com.example.domain

import domain.repository.TradeRepository
import domain.usecase.`interface`.InventoryUseCase
import io.mockk.*
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.assertj.core.api.Assertions.assertThat

@ExtendWith(MockKExtension::class)
class InventoryUseCaseTest {
    
    private lateinit var tradeRepository: TradeRepository
    private lateinit var inventoryUseCase: InventoryUseCase
    
    @BeforeEach
    fun setUp() {
        tradeRepository = mockk()
        inventoryUseCase = DefaultInventoryUseCase(tradeRepository)
    }
    
    @Test
    fun `재고 확인 시 저장소에서 정확한 수량을 반환한다`() {
        // given
        val itemId = "NIKE0001"
        val expectedQuantity = 10
        every { tradeRepository.checkInventory(itemId) } returns expectedQuantity
        
        // when
        val actualQuantity = inventoryUseCase.checkInventory(itemId)
        
        // then
        assertThat(actualQuantity).isEqualTo(expectedQuantity)
        verify(exactly = 1) { tradeRepository.checkInventory(itemId) }
    }
    
    @Test
    fun `재고가 없을 경우 0을 반환한다`() {
        // given
        val itemId = "NIKE0002"
        every { tradeRepository.checkInventory(itemId) } returns 0
        
        // when
        val quantity = inventoryUseCase.checkInventory(itemId)
        
        // then
        assertThat(quantity).isEqualTo(0)
        verify(exactly = 1) { tradeRepository.checkInventory(itemId) }
    }
} 