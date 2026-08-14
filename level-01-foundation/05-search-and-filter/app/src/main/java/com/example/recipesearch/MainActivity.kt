package com.example.recipesearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recipesearch.data.repository.InMemoryRecipeRepository
import com.example.recipesearch.theme.RecipeSearchTheme
import com.example.recipesearch.ui.recipesearch.RecipeSearchRoute
import com.example.recipesearch.ui.recipesearch.RecipeSearchViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    enableEdgeToEdge()
    setContent {
      RecipeSearchTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background,
        ) {
          val viewModel =
            viewModel<RecipeSearchViewModel> {
              RecipeSearchViewModel(InMemoryRecipeRepository())
            }

          RecipeSearchRoute(viewModel = viewModel)
        }
      }
    }
  }
}
