package com.example.checkout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.checkout.data.repository.InMemoryCheckoutRepository
import com.example.checkout.theme.CheckoutTheme
import com.example.checkout.ui.checkout.CheckoutRoute
import com.example.checkout.ui.checkout.CheckoutViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            CheckoutTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val checkoutViewModel =
                        viewModel<CheckoutViewModel> {
                            CheckoutViewModel(InMemoryCheckoutRepository())
                        }

                    CheckoutRoute(viewModel = checkoutViewModel)
                }
            }
        }
    }
}
