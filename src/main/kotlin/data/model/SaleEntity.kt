package com.example.data.model

import java.time.LocalDateTime
import java.util.UUID

data class SaleEntity(
    val id: String = UUID.randomUUID().toString(),
    val itemId: String,
    val quantity: Int,
    val price: Long,
    val totalAmount: Long = quantity * price,
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val isCanceled: Boolean = false
) 