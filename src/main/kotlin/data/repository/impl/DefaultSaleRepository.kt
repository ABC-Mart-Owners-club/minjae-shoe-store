package com.example.data

import com.example.data.mapper.SaleMapper
import com.example.domain.Sale
import com.example.domain.SaleRepository
import java.util.concurrent.ConcurrentHashMap

/**
 * 판매 기록 저장소 구현체
 */
class DefaultSaleRepository(
    private val saleMapper: SaleMapper = SaleMapper()
) : SaleRepository {
    
    // 일단 메모리에 저장하는 걸로 진행(실제에서는 DB 사용하면 될듯)
    private val sales = ConcurrentHashMap<String, MutableList<com.example.data.model.SaleEntity>>()
    
    override fun recordSale(sale: Sale): Boolean {
        val saleEntity = saleMapper.toEntity(sale)
        sales.computeIfAbsent(saleEntity.itemId) { mutableListOf() }.add(saleEntity)
        return true
    }
    
    override fun getTotalSalesByItem(itemId: String): Long {
        return sales[itemId]?.sumOf { 
            if (!it.isCanceled) it.totalAmount else 0L 
        } ?: 0L
    }
    
    override fun getAllSales(): Map<String, Long> {
        val result = mutableMapOf<String, Long>()
        sales.forEach { (itemId, saleList) ->
            val totalAmount = saleList.sumOf { 
                if (!it.isCanceled) it.totalAmount else 0L 
            }
            result[itemId] = totalAmount
        }
        return result
    }
} 