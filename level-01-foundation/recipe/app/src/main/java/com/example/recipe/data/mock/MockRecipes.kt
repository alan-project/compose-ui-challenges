package com.example.recipe.data.mock

import com.example.recipe.data.model.Recipe

object MockRecipes {
  val items =
    listOf(
      Recipe(1, "Avocado Toast with Poached Egg", "Breakfast", 15, 4.8, 410, recipeImageUrl("avocado-toast", 1), true),
      Recipe(2, "Tomato Basil Pasta", "Italian", 30, 4.7, 560, recipeImageUrl("tomato-pasta", 2)),
      Recipe(3, "Fresh Berry Yogurt Bowl", "Breakfast", 10, 4.9, 320, recipeImageUrl("berry-yogurt-bowl", 3)),
      Recipe(4, "Herb Grilled Salmon", "Healthy", 35, 4.8, 490, recipeImageUrl("grilled-salmon", 4), true),
      Recipe(5, "Creamy Mushroom Risotto", "Italian", 40, 4.6, 610, recipeImageUrl("mushroom-risotto", 5)),
      Recipe(6, "Blueberry Pancake Stack", "Breakfast", 20, 4.7, 530, recipeImageUrl("blueberry-pancakes", 6)),
      Recipe(7, "Salmon Power Bowl", "Healthy", 25, 4.5, 450, recipeImageUrl("salmon-power-bowl", 7)),
      Recipe(8, "Mushroom Truffle Rice", "Quick", 25, 4.4, 580, recipeImageUrl("mushroom-rice", 8)),
      Recipe(9, "Berry Overnight Oats", "Breakfast", 8, 4.8, 350, recipeImageUrl("berry-oats", 9), true),
      Recipe(10, "Avocado Garden Plate", "Healthy", 18, 4.6, 390, recipeImageUrl("avocado-plate", 10)),
      Recipe(11, "Rustic Tomato Penne", "Italian", 28, 4.7, 545, recipeImageUrl("tomato-penne", 11)),
      Recipe(12, "Blueberry Sunday Brunch", "Breakfast", 22, 4.9, 570, recipeImageUrl("blueberry-brunch", 12)),
      Recipe(13, "Lemon Salmon Salad", "Quick", 20, 4.5, 430, recipeImageUrl("salmon-salad", 13)),
      Recipe(14, "Parmesan Mushroom Bowl", "Italian", 35, 4.6, 600, recipeImageUrl("mushroom-bowl", 14)),
      Recipe(15, "Ten-Minute Berry Parfait", "Quick", 10, 4.7, 300, recipeImageUrl("berry-parfait", 15)),
      Recipe(16, "Spicy Avocado Toast", "Quick", 12, 4.6, 395, recipeImageUrl("spicy-avocado-toast", 16)),
      Recipe(17, "Roasted Tomato Pasta", "Italian", 32, 4.8, 575, recipeImageUrl("roasted-tomato-pasta", 17), true),
      Recipe(18, "Protein Blueberry Pancakes", "Healthy", 24, 4.5, 460, recipeImageUrl("protein-pancakes", 18)),
    )

  // No API key is required. lock keeps each mock recipe image stable between requests.
  private fun recipeImageUrl(keyword: String, lock: Int): String =
    "https://loremflickr.com/640/640/$keyword?lock=$lock"
}
