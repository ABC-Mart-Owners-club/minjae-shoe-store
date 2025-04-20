package com.example.data.model

import java.util.UUID

data class OrderEntity(
    val id: String = UUID.randomUUID().toString(),
    val itemId: String,
    val quantity: Int,
    val status: OrderStatus = OrderStatus.CREATED,
    val createdAt: Long = System.currentTimeMillis()
)

enum class OrderStatus {
    CREATED, COMPLETED, CANCELLED, PARTIALLY_CANCELLED, FAILED
} 