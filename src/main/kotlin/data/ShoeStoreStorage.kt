package com.example.data

import com.example.domain.Item
import com.example.data.Storage

class ShoeStoreStorage(inventory: MutableMap<String, Int>): Storage {
    private val inventory: MutableMap<String, Int> = inventory
    
    override fun addItem(id: String, quantity: Int): Boolean {
        inventory[id] = inventory[id]?.plus(quantity) ?: quantity
        return true
    }

    override fun substractItem(id: String, quantity: Int): Boolean {
        if (inventory[id] == null || inventory[id]!! < quantity) {
            return false
        }

        inventory[id] = inventory[id]?.minus(quantity) ?: 0
        return true
    }

    override fun checkInventory(id: String): Int {
        return inventory[id] ?: 0
    }
}