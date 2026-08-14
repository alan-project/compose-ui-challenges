package com.example.shoppingcart.data.repository

import com.example.shoppingcart.data.model.CartItem
import com.example.shoppingcart.data.model.RemovedCartItem
import kotlinx.coroutines.flow.StateFlow

interface ShoppingCartRepository {
    val items: StateFlow<List<CartItem>>

    fun toggleItemSelection(itemId: Long)

    fun setAllItemsSelected(isSelected: Boolean)

    fun incrementQuantity(itemId: Long)

    fun decrementQuantity(itemId: Long)

    fun removeItem(itemId: Long): RemovedCartItem?

    fun restoreItem(removedItem: RemovedCartItem)
}
