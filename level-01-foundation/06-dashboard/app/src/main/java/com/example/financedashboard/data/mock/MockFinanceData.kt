package com.example.financedashboard.data.mock

import com.example.financedashboard.data.model.BudgetCategory
import com.example.financedashboard.data.model.FinanceDashboard
import com.example.financedashboard.data.model.SpendingPoint
import com.example.financedashboard.data.model.Transaction
import com.example.financedashboard.data.model.TransactionType

object MockFinanceData {
  val dashboard =
    FinanceDashboard(
      userName = "Maya",
      balance = 12_480.62,
      monthlyIncome = 6_840.00,
      monthlyExpense = 3_275.45,
      savingsCurrent = 7_200.00,
      savingsGoal = 10_000.00,
      weeklySpending =
        listOf(
          SpendingPoint("M", 82.0),
          SpendingPoint("T", 126.0),
          SpendingPoint("W", 64.0),
          SpendingPoint("T", 148.0),
          SpendingPoint("F", 104.0),
          SpendingPoint("S", 176.0),
          SpendingPoint("S", 92.0),
        ),
      budgets =
        listOf(
          BudgetCategory("Food", 468.0, 650.0),
          BudgetCategory("Transport", 215.0, 400.0),
          BudgetCategory("Shopping", 392.0, 500.0),
        ),
      transactions =
        listOf(
          Transaction(1, "Salary deposit", "Income", "Today", 3_420.00, TransactionType.INCOME),
          Transaction(2, "Neighbourhood market", "Groceries", "Today", 86.42, TransactionType.EXPENSE),
          Transaction(3, "Transit pass", "Transport", "Yesterday", 52.00, TransactionType.EXPENSE),
          Transaction(4, "Freelance project", "Income", "Aug 11", 680.00, TransactionType.INCOME),
          Transaction(5, "Home supplies", "Shopping", "Aug 10", 124.80, TransactionType.EXPENSE),
        ),
    )
}
