package com.example.financedashboard.ui.dashboard

import androidx.lifecycle.ViewModel
import com.example.financedashboard.data.repository.FinanceRepository

class DashboardViewModel(
  private val financeRepository: FinanceRepository,
) : ViewModel() {
}
