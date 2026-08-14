package com.example.recipesearch.ui.recipesearch

import androidx.lifecycle.ViewModel
import com.example.recipesearch.data.repository.RecipeRepository

class RecipeSearchViewModel(
  private val recipeRepository: RecipeRepository,
) : ViewModel() {
}
