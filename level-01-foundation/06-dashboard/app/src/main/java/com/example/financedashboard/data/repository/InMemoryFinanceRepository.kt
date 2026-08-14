package com.example.financedashboard.data.repository

import com.example.financedashboard.data.mock.MockFinanceData
import com.example.financedashboard.data.model.FinanceDashboard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class InMemoryFinanceRepository : FinanceRepository {
  private val _dashboard = MutableStateFlow(MockFinanceData.dashboard)

  override val dashboard: StateFlow<FinanceDashboard> = _dashboard.asStateFlow()
}
