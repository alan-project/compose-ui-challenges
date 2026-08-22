package com.example.recipe.ui.recipe

import com.example.recipe.data.mock.MockRecipes
import com.example.recipe.data.repository.RecipeRepositoryImpl
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
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
  fun favoriteAction_updatesRepository() = runTest {
    val repository = RecipeRepositoryImpl()
    val viewModel = RecipeViewModel(repository)
    val recipe = repository.recipes.first().first { !it.isFavorite }

    viewModel.onFavoriteClick(recipe.id)

    assertEquals(true, repository.recipes.first().first { it.id == recipe.id }.isFavorite)
  }
}
