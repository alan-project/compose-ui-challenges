package com.example.shoppingcart.data.mock

import com.example.shoppingcart.data.model.CartItem
import com.example.shoppingcart.data.model.ProductImage

object MockCartItems {
    val items =
        listOf(
            CartItem(
                id = 1,
                name = "Studio Headphones",
                variant = "Oat · Wireless",
                unitPriceCents = 17_900,
                image = ProductImage.HEADPHONES,
            ),
            CartItem(
                id = 2,
                name = "Mechanical Keyboard",
                variant = "Sage · US layout",
                unitPriceCents = 11_900,
                image = ProductImage.KEYBOARD,
            ),
            CartItem(
                id = 3,
                name = "Move Fitness Watch",
                variant = "Clay · 42 mm",
                unitPriceCents = 15_900,
                image = ProductImage.SMARTWATCH,
                isSelected = false,
            ),
            CartItem(
                id = 4,
                name = "Everyday Backpack",
                variant = "Olive · 18 L",
                unitPriceCents = 8_900,
                image = ProductImage.BACKPACK,
                isSelected = false,
            ),
        )
}
