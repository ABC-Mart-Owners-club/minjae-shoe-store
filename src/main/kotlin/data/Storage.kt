package com.example.data

interface Storage {
    fun addItem(id: String, quantity: Int): Boolean
    fun substractItem(id: String, quantity: Int): Boolean
    fun checkInventory(id: String): Int
}