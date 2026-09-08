package com.example.burgerbuilder.data.model

import kotlinx.collections.immutable.ImmutableList

data class Burger(
    val id: Long,
    val name: String,
    val description: String,
    val basePrice: Double,
    val imageUrl: String,
    val doublePattyPrice: Double,
    val vegetables: ImmutableList<BurgerOption>,
    val extras: ImmutableList<BurgerOption>,
)

data class BurgerOption(
    val id: String,
    val name: String,
    val additionalPrice: Double = 0.0,
    val selectedByDefault: Boolean = false,
)
