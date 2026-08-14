package com.example.recipesearch.data.model

enum class RecipeImage {
  AVOCADO_TOAST,
  TOMATO_PASTA,
  BERRY_BOWL,
  GRILLED_SALMON,
  MUSHROOM_RISOTTO,
  BLUEBERRY_PANCAKES,
}

data class Recipe(
  val id: Long,
  val name: String,
  val category: String,
  val durationMinutes: Int,
  val rating: Double,
  val calories: Int,
  val image: RecipeImage,
  val isFavorite: Boolean = false,
)
