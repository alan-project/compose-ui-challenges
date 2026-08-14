package com.example.financedashboard.data.repository

import com.example.financedashboard.data.model.FinanceDashboard
import kotlinx.coroutines.flow.StateFlow

interface FinanceRepository {
  val dashboard: StateFlow<FinanceDashboard>
}
