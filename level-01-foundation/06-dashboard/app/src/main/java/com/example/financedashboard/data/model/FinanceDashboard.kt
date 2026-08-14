package com.example.financedashboard.data.model

data class SpendingPoint(
  val day: String,
  val amount: Double,
)

data class BudgetCategory(
  val name: String,
  val spent: Double,
  val limit: Double,
)

enum class TransactionType {
  INCOME,
  EXPENSE,
}

data class Transaction(
  val id: Long,
  val title: String,
  val category: String,
  val date: String,
  val amount: Double,
  val type: TransactionType,
)

data class FinanceDashboard(
  val userName: String,
  val balance: Double,
  val monthlyIncome: Double,
  val monthlyExpense: Double,
  val savingsCurrent: Double,
  val savingsGoal: Double,
  val weeklySpending: List<SpendingPoint>,
  val budgets: List<BudgetCategory>,
  val transactions: List<Transaction>,
)
