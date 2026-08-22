package com.example.composemarket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composemarket.data.repository.ProductRepositoryImpl
import com.example.composemarket.theme.ComposeMarketTheme
import com.example.composemarket.ui.composemarket.ComposeMarketRoute
import com.example.composemarket.ui.composemarket.ComposeMarketViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ComposeMarketTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val viewModel = viewModel<ComposeMarketViewModel> {
                        ComposeMarketViewModel(ProductRepositoryImpl())
                    }

                    ComposeMarketRoute(viewModel = viewModel)
                }
            }
        }
    }
}
