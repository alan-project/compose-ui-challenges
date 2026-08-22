package com.example.recipe.data.repository

import com.example.recipe.data.mock.MockRecipes
import com.example.recipe.data.model.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RecipeRepositoryImpl : RecipeRepository {
  private val _recipes = MutableStateFlow(MockRecipes.items)

  override val recipes: StateFlow<List<Recipe>> = _recipes.asStateFlow()

  override fun toggleFavorite(recipeId: Long) {
    _recipes.update { recipes ->
      recipes.map { recipe ->
        if (recipe.id == recipeId) {
          recipe.copy(isFavorite = !recipe.isFavorite)
        } else {
          recipe
        }
      }
    }
  }
}
