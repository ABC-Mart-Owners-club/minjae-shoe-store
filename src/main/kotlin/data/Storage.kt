package data

interface Storage {
    fun addItem(id: String, quantity: Int): Boolean
    fun subtractItem(id: String, quantity: Int): Boolean
    fun checkInventory(id: String): Int
}