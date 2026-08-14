package com.example.recipesearch.data.mock

import com.example.recipesearch.data.model.Recipe
import com.example.recipesearch.data.model.RecipeImage

object MockRecipes {
  val items =
    listOf(
      Recipe(1, "Avocado Toast with Poached Egg", "Breakfast", 15, 4.8, 410, RecipeImage.AVOCADO_TOAST, true),
      Recipe(2, "Tomato Basil Pasta", "Italian", 30, 4.7, 560, RecipeImage.TOMATO_PASTA),
      Recipe(3, "Fresh Berry Yogurt Bowl", "Breakfast", 10, 4.9, 320, RecipeImage.BERRY_BOWL),
      Recipe(4, "Herb Grilled Salmon", "Healthy", 35, 4.8, 490, RecipeImage.GRILLED_SALMON, true),
      Recipe(5, "Creamy Mushroom Risotto", "Italian", 40, 4.6, 610, RecipeImage.MUSHROOM_RISOTTO),
      Recipe(6, "Blueberry Pancake Stack", "Breakfast", 20, 4.7, 530, RecipeImage.BLUEBERRY_PANCAKES),
      Recipe(7, "Salmon Power Bowl", "Healthy", 25, 4.5, 450, RecipeImage.GRILLED_SALMON),
      Recipe(8, "Mushroom Truffle Rice", "Quick", 25, 4.4, 580, RecipeImage.MUSHROOM_RISOTTO),
      Recipe(9, "Berry Overnight Oats", "Breakfast", 8, 4.8, 350, RecipeImage.BERRY_BOWL, true),
      Recipe(10, "Avocado Garden Plate", "Healthy", 18, 4.6, 390, RecipeImage.AVOCADO_TOAST),
      Recipe(11, "Rustic Tomato Penne", "Italian", 28, 4.7, 545, RecipeImage.TOMATO_PASTA),
      Recipe(12, "Blueberry Sunday Brunch", "Breakfast", 22, 4.9, 570, RecipeImage.BLUEBERRY_PANCAKES),
      Recipe(13, "Lemon Salmon Salad", "Quick", 20, 4.5, 430, RecipeImage.GRILLED_SALMON),
      Recipe(14, "Parmesan Mushroom Bowl", "Italian", 35, 4.6, 600, RecipeImage.MUSHROOM_RISOTTO),
      Recipe(15, "Ten-Minute Berry Parfait", "Quick", 10, 4.7, 300, RecipeImage.BERRY_BOWL),
      Recipe(16, "Spicy Avocado Toast", "Quick", 12, 4.6, 395, RecipeImage.AVOCADO_TOAST),
      Recipe(17, "Roasted Tomato Pasta", "Italian", 32, 4.8, 575, RecipeImage.TOMATO_PASTA, true),
      Recipe(18, "Protein Blueberry Pancakes", "Healthy", 24, 4.5, 460, RecipeImage.BLUEBERRY_PANCAKES),
    )
}
