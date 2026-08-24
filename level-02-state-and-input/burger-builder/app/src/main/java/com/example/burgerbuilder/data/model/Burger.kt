package com.example.burgerbuilder.data.model

data class Burger(
  val id: Long,
  val name: String,
  val description: String,
  val basePrice: Double,
  val imageUrl: String,
  val doublePattyPrice: Double,
  val vegetables: List<BurgerOption>,
  val extras: List<BurgerOption>,
)

data class BurgerOption(
  val id: String,
  val name: String,
  val additionalPrice: Double = 0.0,
  val selectedByDefault: Boolean = false,
)
