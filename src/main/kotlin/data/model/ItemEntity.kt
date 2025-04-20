package com.example.data.model

data class ItemEntity(
    val id: String,
    val name: String,
    val price: Long,
    val brandCode: String,
    val stock: Int = 0,
    val isActive: Boolean = true
) 