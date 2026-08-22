package com.example.recipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recipe.data.repository.RecipeRepositoryImpl
import com.example.recipe.theme.RecipeTheme
import com.example.recipe.ui.recipe.RecipeRoute
import com.example.recipe.ui.recipe.RecipeViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    enableEdgeToEdge()
    setContent {
      RecipeTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background,
        ) {
          val viewModel =
            viewModel<RecipeViewModel> {
              RecipeViewModel(RecipeRepositoryImpl())
            }

          RecipeRoute(viewModel = viewModel)
        }
      }
    }
  }
}
