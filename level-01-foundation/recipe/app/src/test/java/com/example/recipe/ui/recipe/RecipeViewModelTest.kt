package com.example.recipe.ui.recipe

import com.example.recipe.data.mock.MockRecipes
import com.example.recipe.data.repository.RecipeRepositoryImpl
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class RecipeViewModelTest {
  @Test
  fun filterRecipes_matchesQueryAndCategory() {
    val result = filterRecipes(MockRecipes.items, query = "berry", category = "Breakfast")

    assertTrue(result.isNotEmpty())
    assertTrue(result.all { it.name.contains("berry", ignoreCase = true) && it.category == "Breakfast" })
  }

  @Test
  fun favoriteAction_updatesRepository() {
    val repository = RecipeRepositoryImpl()
    val viewModel = RecipeViewModel(repository)
    val recipe = repository.recipes.value.first { !it.isFavorite }

    viewModel.onFavoriteClick(recipe.id)

    assertEquals(true, repository.recipes.value.first { it.id == recipe.id }.isFavorite)
  }
}
