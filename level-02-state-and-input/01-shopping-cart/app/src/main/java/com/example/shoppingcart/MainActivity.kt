package com.example.shoppingcart

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shoppingcart.data.repository.InMemoryShoppingCartRepository
import com.example.shoppingcart.theme.ShoppingCartTheme
import com.example.shoppingcart.ui.cart.ShoppingCartRoute
import com.example.shoppingcart.ui.cart.ShoppingCartViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }
        setContent {
            ShoppingCartTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val viewModel =
                        viewModel<ShoppingCartViewModel> {
                            ShoppingCartViewModel(InMemoryShoppingCartRepository())
                        }

                    ShoppingCartRoute(viewModel = viewModel)
                }
            }
        }
    }
}
