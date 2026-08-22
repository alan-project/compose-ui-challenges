package com.example.recipe.data.repository

import com.example.recipe.data.model.Recipe
import kotlinx.coroutines.flow.StateFlow

interface RecipeRepository {
  val recipes: StateFlow<List<Recipe>>

  fun toggleFavorite(recipeId: Long)
}
