package com.example.financedashboard.ui.dashboard

import com.example.financedashboard.data.mock.MockFinanceData
import com.example.financedashboard.data.repository.InMemoryFinanceRepository
import org.junit.Assert.assertEquals
import org.junit.Test

class DashboardViewModelTest {
  @Test
  fun dashboard_exposesRepositoryData() {
    val viewModel = DashboardViewModel(InMemoryFinanceRepository())

    assertEquals(MockFinanceData.dashboard, viewModel.dashboard.value)
  }
}
