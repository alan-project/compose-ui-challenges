package com.example.recipesearch.ui.recipesearch

import com.example.recipesearch.data.mock.MockRecipes
import com.example.recipesearch.data.repository.InMemoryRecipeRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class RecipeSearchViewModelTest {
  @Test
  fun filterRecipes_matchesQueryAndCategory() {
    val result = filterRecipes(MockRecipes.items, query = "berry", category = "Breakfast")

    assertTrue(result.isNotEmpty())
    assertTrue(result.all { it.name.contains("berry", ignoreCase = true) && it.category == "Breakfast" })
  }

  @Test
  fun favoriteAction_updatesRepository() {
    val repository = InMemoryRecipeRepository()
    val viewModel = RecipeSearchViewModel(repository)
    val recipe = repository.recipes.value.first { !it.isFavorite }

    viewModel.onFavoriteClick(recipe.id)

    assertEquals(true, repository.recipes.value.first { it.id == recipe.id }.isFavorite)
  }
}
