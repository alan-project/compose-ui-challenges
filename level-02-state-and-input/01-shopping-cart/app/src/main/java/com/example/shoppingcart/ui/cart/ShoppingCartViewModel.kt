package com.example.shoppingcart.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingcart.data.model.CartItem
import com.example.shoppingcart.data.model.CartRules
import com.example.shoppingcart.data.model.RemovedCartItem
import com.example.shoppingcart.data.repository.ShoppingCartRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class ShoppingCartUiState(
    val items: List<CartItem> = emptyList(),
    val totalQuantity: Int = 0,
    val selectedLineCount: Int = 0,
    val selectedQuantity: Int = 0,
    val allSelected: Boolean = false,
    val subtotalCents: Long = 0,
    val shippingCents: Long = 0,
    val totalCents: Long = 0,
    val remainingForFreeShippingCents: Long = CartRules.FREE_SHIPPING_THRESHOLD_CENTS,
    val checkoutEnabled: Boolean = false,
    val pendingUndo: RemovedCartItem? = null,
)

sealed interface ShoppingCartEvent {
    data class ItemRemoved(
        val removedItem: RemovedCartItem,
    ) : ShoppingCartEvent

    data class CheckoutPreview(
        val selectedQuantity: Int,
    ) : ShoppingCartEvent
}

@OptIn(ExperimentalCoroutinesApi::class)
class ShoppingCartViewModel(
    private val repository: ShoppingCartRepository,
) : ViewModel() {
    private val pendingUndo = MutableStateFlow<RemovedCartItem?>(null)

    val uiState: StateFlow<ShoppingCartUiState> =
        combine(repository.items, pendingUndo) { items, removedItem ->
            createShoppingCartUiState(items = items, pendingUndo = removedItem)
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = createShoppingCartUiState(repository.items.value),
            )

    private val _events =
        MutableSharedFlow<ShoppingCartEvent>(
            replay = 1,
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST,
        )
    val events: SharedFlow<ShoppingCartEvent> = _events.asSharedFlow()

    fun onToggleItemSelection(itemId: Long) {
        repository.toggleItemSelection(itemId)
    }

    fun onSelectAll(isSelected: Boolean) {
        repository.setAllItemsSelected(isSelected)
    }

    fun onIncrementQuantity(itemId: Long) {
        repository.incrementQuantity(itemId)
    }

    fun onDecrementQuantity(itemId: Long) {
        repository.decrementQuantity(itemId)
    }

    fun onRemoveItem(itemId: Long) {
        repository.removeItem(itemId)?.let { removedItem ->
            pendingUndo.value = removedItem
            _events.tryEmit(ShoppingCartEvent.ItemRemoved(removedItem))
        }
    }

    fun onUndoRemove(removedItem: RemovedCartItem) {
        if (pendingUndo.value != removedItem) return

        repository.restoreItem(removedItem)
        pendingUndo.value = null
        clearEventIfCurrent(ShoppingCartEvent.ItemRemoved(removedItem))
    }

    fun onEventConsumed(event: ShoppingCartEvent) {
        if (event is ShoppingCartEvent.ItemRemoved && pendingUndo.value == event.removedItem) {
            pendingUndo.value = null
        }
        clearEventIfCurrent(event)
    }

    fun onPreviewCheckoutClick() {
        val state = createShoppingCartUiState(repository.items.value)
        if (state.checkoutEnabled) {
            pendingUndo.value = null
            _events.tryEmit(ShoppingCartEvent.CheckoutPreview(state.selectedQuantity))
        }
    }

    private fun clearEventIfCurrent(event: ShoppingCartEvent) {
        if (_events.replayCache.lastOrNull() == event) {
            _events.resetReplayCache()
        }
    }
}

internal fun createShoppingCartUiState(
    items: List<CartItem>,
    pendingUndo: RemovedCartItem? = null,
): ShoppingCartUiState {
    val selectedItems = items.filter(CartItem::isSelected)
    val subtotalCents =
        selectedItems.sumOf { item -> item.unitPriceCents * item.quantity.toLong() }
    val shippingCents =
        when {
            subtotalCents == 0L -> 0L
            subtotalCents >= CartRules.FREE_SHIPPING_THRESHOLD_CENTS -> 0L
            else -> CartRules.STANDARD_SHIPPING_CENTS
        }

    return ShoppingCartUiState(
        items = items,
        totalQuantity = items.sumOf(CartItem::quantity),
        selectedLineCount = selectedItems.size,
        selectedQuantity = selectedItems.sumOf(CartItem::quantity),
        allSelected = items.isNotEmpty() && selectedItems.size == items.size,
        subtotalCents = subtotalCents,
        shippingCents = shippingCents,
        totalCents = subtotalCents + shippingCents,
        remainingForFreeShippingCents =
            (CartRules.FREE_SHIPPING_THRESHOLD_CENTS - subtotalCents).coerceAtLeast(0L),
        checkoutEnabled = selectedItems.isNotEmpty(),
        pendingUndo = pendingUndo,
    )
}
