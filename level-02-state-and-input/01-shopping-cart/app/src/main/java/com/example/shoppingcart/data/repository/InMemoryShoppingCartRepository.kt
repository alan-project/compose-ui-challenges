package com.example.shoppingcart.data.repository

import com.example.shoppingcart.data.mock.MockCartItems
import com.example.shoppingcart.data.model.CartItem
import com.example.shoppingcart.data.model.CartRules
import com.example.shoppingcart.data.model.RemovedCartItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InMemoryShoppingCartRepository(
    initialItems: List<CartItem> = MockCartItems.items,
) : ShoppingCartRepository {
    private val originalOrderById =
        initialItems.mapIndexed { index, item -> item.id to index }.toMap()

    private val _items =
        MutableStateFlow(
            initialItems.map { item ->
                item.copy(
                    quantity =
                        item.quantity.coerceIn(
                            CartRules.MIN_QUANTITY,
                            CartRules.MAX_QUANTITY,
                        ),
                )
            },
        )

    override val items: StateFlow<List<CartItem>> = _items.asStateFlow()

    override fun toggleItemSelection(itemId: Long) {
        updateItem(itemId) { item -> item.copy(isSelected = !item.isSelected) }
    }

    override fun setAllItemsSelected(isSelected: Boolean) {
        _items.update { items -> items.map { item -> item.copy(isSelected = isSelected) } }
    }

    override fun incrementQuantity(itemId: Long) {
        updateItem(itemId) { item ->
            item.copy(quantity = (item.quantity + 1).coerceAtMost(CartRules.MAX_QUANTITY))
        }
    }

    override fun decrementQuantity(itemId: Long) {
        updateItem(itemId) { item ->
            item.copy(quantity = (item.quantity - 1).coerceAtLeast(CartRules.MIN_QUANTITY))
        }
    }

    @Synchronized
    override fun removeItem(itemId: Long): RemovedCartItem? {
        val currentItems = _items.value
        val index = currentItems.indexOfFirst { item -> item.id == itemId }
        if (index == -1) return null

        val removedItem =
            RemovedCartItem(
                item = currentItems[index],
                originalOrder = originalOrderById.getValue(itemId),
            )
        _items.value = currentItems.toMutableList().apply { removeAt(index) }
        return removedItem
    }

    @Synchronized
    override fun restoreItem(removedItem: RemovedCartItem) {
        val currentItems = _items.value
        if (currentItems.any { item -> item.id == removedItem.item.id }) return

        _items.value =
            (currentItems + removedItem.item).sortedBy { item ->
                originalOrderById[item.id] ?: removedItem.originalOrder
            }
    }

    private fun updateItem(
        itemId: Long,
        transform: (CartItem) -> CartItem,
    ) {
        _items.update { items ->
            items.map { item -> if (item.id == itemId) transform(item) else item }
        }
    }
}
