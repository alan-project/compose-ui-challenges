package com.example.recipe.ui.recipe

import androidx.lifecycle.ViewModel
import com.example.recipe.data.repository.RecipeRepository

class RecipeViewModel(
  private val recipeRepository: RecipeRepository,
) : ViewModel() {
}
