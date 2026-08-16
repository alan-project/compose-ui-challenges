package com.example.productgrid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.productgrid.data.repository.ProductRepositoryImpl
import com.example.productgrid.theme.ProductGridTheme
import com.example.productgrid.ui.productgrid.ProductGridRoute
import com.example.productgrid.ui.productgrid.ProductGridViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ProductGridTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val viewModel = viewModel<ProductGridViewModel> {
                        ProductGridViewModel(ProductRepositoryImpl())
                    }

                    ProductGridRoute(viewModel = viewModel)
                }
            }
        }
    }
}
