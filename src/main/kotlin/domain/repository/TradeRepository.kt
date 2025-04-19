package com.example.domain

import com.example.domain.Order

interface TradeRepository {
    fun requestOrder(order: Order);
    fun cancelOrder(order: Order);
    fun partialCancelOrder(order: Order);
    fun checkInventory(id: String): Int;
}