package com.example.recipesearch.data.repository

import com.example.recipesearch.data.model.Recipe
import kotlinx.coroutines.flow.StateFlow

interface RecipeRepository {
  val recipes: StateFlow<List<Recipe>>

  fun toggleFavorite(recipeId: Long)
}
