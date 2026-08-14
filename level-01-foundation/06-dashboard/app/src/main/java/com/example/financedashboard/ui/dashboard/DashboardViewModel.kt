package com.example.financedashboard.ui.dashboard

import androidx.lifecycle.ViewModel
import com.example.financedashboard.data.model.FinanceDashboard
import com.example.financedashboard.data.mock.MockFinanceData
import com.example.financedashboard.data.repository.FinanceRepository
import kotlinx.coroutines.flow.StateFlow

data class DashboardUiState(
  val dashboard: FinanceDashboard = MockFinanceData.dashboard,
)

class DashboardViewModel(
  private val financeRepository: FinanceRepository,
) : ViewModel() {
  val dashboard: StateFlow<FinanceDashboard> = financeRepository.dashboard
}
