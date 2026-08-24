package com.example.burgerbuilder.data.mock

import com.example.burgerbuilder.data.model.Burger
import com.example.burgerbuilder.data.model.BurgerOption
import kotlinx.collections.immutable.persistentListOf

object MockBurger {
  val item =
    Burger(
      id = 1L,
      name = "Classic Smash Burger",
      description = "A smashed beef patty with cheddar, house sauce, and a toasted brioche bun.",
      basePrice = 10.99,
      imageUrl =
        "https://images.unsplash.com/photo-1568901346375-23c9450c58cd" +
          "?auto=format&fit=crop&w=1200&q=85",
      doublePattyPrice = 3.00,
      vegetables =
        persistentListOf(
          BurgerOption("lettuce", "Lettuce", selectedByDefault = true),
          BurgerOption("tomato", "Tomato", selectedByDefault = true),
          BurgerOption("onion", "Red onion", selectedByDefault = true),
          BurgerOption("pickles", "Pickles", selectedByDefault = true),
          BurgerOption("jalapenos", "Jalapeños"),
        ),
      extras =
        persistentListOf(
          BurgerOption("cheddar", "Extra cheddar", additionalPrice = 0.80),
          BurgerOption("bacon", "Crispy bacon", additionalPrice = 2.00),
          BurgerOption("egg", "Fried egg", additionalPrice = 1.50),
          BurgerOption("avocado", "Avocado", additionalPrice = 1.50),
        ),
    )
}
