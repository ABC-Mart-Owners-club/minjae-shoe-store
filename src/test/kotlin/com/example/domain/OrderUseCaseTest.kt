package com.example.domain

import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import io.mockk.junit5.MockKExtension

@ExtendWith(MockKExtension::class)
class OrderUseCaseTest {
    
    private lateinit var tradeRepository: TradeRepository
    private lateinit var orderUseCase: OrderUseCase
    
    @BeforeEach
    fun setUp() {
        tradeRepository = mockk()
        orderUseCase = DefaultOrderUseCase(tradeRepository)
    }
    
    @Test
    fun `주문 요청 시 저장소에 요청이 전달된다`() {
        // given
        val order = Order("NIKE0001", 3)
        every { tradeRepository.requestOrder(order) } just Runs
        
        // when
        orderUseCase.requestOrder(order)
        
        // then
        verify(exactly = 1) { tradeRepository.requestOrder(order) }
    }
    
    @Test
    fun `주문 취소 시 저장소에 취소 요청이 전달된다`() {
        // given
        val order = Order("NIKE0001", 3)
        every { tradeRepository.cancelOrder(order) } just Runs
        
        // when
        orderUseCase.cancelOrder(order)
        
        // then
        verify(exactly = 1) { tradeRepository.cancelOrder(order) }
    }
    
    @Test
    fun `주문 부분 취소 시 저장소에 부분 취소 요청이 전달된다`() {
        // given
        val order = Order("NIKE0001", 2)
        every { tradeRepository.partialCancelOrder(order) } just Runs
        
        // when
        orderUseCase.partialCancelOrder(order)
        
        // then
        verify(exactly = 1) { tradeRepository.partialCancelOrder(order) }
    }
} 