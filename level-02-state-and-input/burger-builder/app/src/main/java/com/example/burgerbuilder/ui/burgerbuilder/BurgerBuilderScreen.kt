package com.example.burgerbuilder.ui.burgerbuilder

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.burgerbuilder.data.model.Burger

@Composable
fun BurgerBuilderRoute(
  viewModel: BurgerBuilderViewModel,
  modifier: Modifier = Modifier,
) {
  val burger by viewModel.burger.collectAsStateWithLifecycle()

  BurgerBuilderScreen(
    burger = burger,
    modifier = modifier,
  )
}

@Composable
fun BurgerBuilderScreen(
  burger: Burger,
  modifier: Modifier = Modifier,
) {
  // TODO: Build a customization screen with a compact burger image and order summary.
  // Keep patty, quantity, and notes on the main screen. Open separate ModalBottomSheets
  // for vegetables and extras, with rememberSaveable state owned above those sheets.
  Box(
    modifier = modifier.fillMaxSize(),
    contentAlignment = Alignment.Center,
  ) {
    Text(
      text = "Build the ${burger.name} screen",
      style = MaterialTheme.typography.titleLarge,
    )
  }
}
