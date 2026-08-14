package com.example.financedashboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.financedashboard.data.repository.InMemoryFinanceRepository
import com.example.financedashboard.theme.FinanceDashboardTheme
import com.example.financedashboard.ui.dashboard.DashboardRoute
import com.example.financedashboard.ui.dashboard.DashboardViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    enableEdgeToEdge()
    setContent {
      FinanceDashboardTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background,
        ) {
          val viewModel =
            viewModel<DashboardViewModel> {
              DashboardViewModel(InMemoryFinanceRepository())
            }

          DashboardRoute(viewModel = viewModel)
        }
      }
    }
  }
}
