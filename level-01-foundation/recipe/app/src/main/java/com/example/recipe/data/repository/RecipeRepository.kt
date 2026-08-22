package com.example.recipe.data.repository

import com.example.recipe.data.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
  val recipes: Flow<List<Recipe>>

  fun toggleFavorite(recipeId: Long)
}
