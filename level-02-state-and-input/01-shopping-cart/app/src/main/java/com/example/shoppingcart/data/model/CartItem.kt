package com.example.shoppingcart.data.model

enum class ProductImage {
    HEADPHONES,
    KEYBOARD,
    SMARTWATCH,
    BACKPACK,
}

data class CartItem(
    val id: Long,
    val name: String,
    val variant: String,
    val unitPriceCents: Long,
    val image: ProductImage,
    val quantity: Int = CartRules.MIN_QUANTITY,
    val isSelected: Boolean = true,
)

data class RemovedCartItem(
    val item: CartItem,
    val originalOrder: Int,
)

object CartRules {
    const val MIN_QUANTITY = 1
    const val MAX_QUANTITY = 10
    const val STANDARD_SHIPPING_CENTS = 1_200L
    const val FREE_SHIPPING_THRESHOLD_CENTS = 40_000L
}
