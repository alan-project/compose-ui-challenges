package com.example.productdetail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.productdetail.data.repository.InMemoryProductDetailRepository
import com.example.productdetail.theme.ProductDetailTheme
import com.example.productdetail.ui.productdetail.ProductDetailRoute
import com.example.productdetail.ui.productdetail.ProductDetailViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    enableEdgeToEdge()
    setContent {
      ProductDetailTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background,
        ) {
          val viewModel =
            viewModel<ProductDetailViewModel> {
              ProductDetailViewModel(InMemoryProductDetailRepository())
            }

          ProductDetailRoute(viewModel = viewModel)
        }
      }
    }
  }
}
