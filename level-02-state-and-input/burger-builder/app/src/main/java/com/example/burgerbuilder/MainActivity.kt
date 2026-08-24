package com.example.burgerbuilder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.burgerbuilder.data.repository.BurgerRepositoryImpl
import com.example.burgerbuilder.theme.BurgerBuilderTheme
import com.example.burgerbuilder.ui.burgerbuilder.BurgerBuilderRoute
import com.example.burgerbuilder.ui.burgerbuilder.BurgerBuilderViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    enableEdgeToEdge()
    setContent {
      BurgerBuilderTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background,
        ) {
          val viewModel =
            viewModel<BurgerBuilderViewModel> {
              BurgerBuilderViewModel(BurgerRepositoryImpl())
            }

          BurgerBuilderRoute(viewModel = viewModel)
        }
      }
    }
  }
}
