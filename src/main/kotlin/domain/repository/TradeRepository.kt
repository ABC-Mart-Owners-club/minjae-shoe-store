package com.example.domain

import com.example.domain.Order

interface TradeRepository {
    fun saledItem(order: Order);
    fun canceledItem(order: Order);
    fun partialCancelOrder(order: Order);
    fun checkInventory(id: String): Int;
}