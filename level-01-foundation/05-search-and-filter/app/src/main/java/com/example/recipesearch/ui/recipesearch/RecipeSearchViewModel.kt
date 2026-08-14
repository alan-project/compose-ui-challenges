package com.example.recipesearch.ui.recipesearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesearch.data.model.Recipe
import com.example.recipesearch.data.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class RecipeSearchUiState(
  val query: String = "",
  val selectedCategory: String = "All",
  val categories: List<String> = listOf("All", "Breakfast", "Italian", "Healthy", "Quick"),
  val recipes: List<Recipe> = emptyList(),
)

class RecipeSearchViewModel(
  private val recipeRepository: RecipeRepository,
) : ViewModel() {
  private val query = MutableStateFlow("")
  private val selectedCategory = MutableStateFlow("All")

  val uiState: StateFlow<RecipeSearchUiState> =
    combine(recipeRepository.recipes, query, selectedCategory) { recipes, query, category ->
        RecipeSearchUiState(
          query = query,
          selectedCategory = category,
          recipes = filterRecipes(recipes, query, category),
        )
      }
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = RecipeSearchUiState(recipes = recipeRepository.recipes.value),
      )

  fun onQueryChanged(value: String) {
    query.value = value
  }

  fun onCategorySelected(category: String) {
    selectedCategory.value = category
  }

  fun onFavoriteClick(recipeId: Long) {
    recipeRepository.toggleFavorite(recipeId)
  }
}

internal fun filterRecipes(recipes: List<Recipe>, query: String, category: String): List<Recipe> =
  recipes.filter { recipe ->
    val matchesQuery = query.isBlank() || recipe.name.contains(query.trim(), ignoreCase = true)
    val matchesCategory = category == "All" || recipe.category == category
    matchesQuery && matchesCategory
  }
