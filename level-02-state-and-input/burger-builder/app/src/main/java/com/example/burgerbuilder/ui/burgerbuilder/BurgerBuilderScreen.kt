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
  // TODO: Build the product page and open its options in a ModalBottomSheet.
  // Keep in-progress selections with rememberSaveable above the sheet content.
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
