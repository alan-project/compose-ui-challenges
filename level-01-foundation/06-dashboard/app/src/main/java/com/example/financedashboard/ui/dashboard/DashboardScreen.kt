package com.example.financedashboard.ui.dashboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.financedashboard.R
import com.example.financedashboard.data.mock.MockFinanceData
import com.example.financedashboard.data.model.BudgetCategory
import com.example.financedashboard.data.model.FinanceDashboard
import com.example.financedashboard.data.model.SpendingPoint
import com.example.financedashboard.data.model.Transaction
import com.example.financedashboard.data.model.TransactionType
import com.example.financedashboard.theme.DashboardNavy
import com.example.financedashboard.theme.FinanceDashboardTheme
import com.example.financedashboard.theme.MintHighlight
import com.example.financedashboard.theme.OnDashboardNavy
import java.text.DecimalFormat

@Composable
fun DashboardRoute(
  viewModel: DashboardViewModel,
  modifier: Modifier = Modifier,
) {
  val dashboard by viewModel.dashboard.collectAsStateWithLifecycle()
  DashboardScreen(dashboard = dashboard, modifier = modifier)
}

@Composable
fun DashboardScreen(
  dashboard: FinanceDashboard,
  modifier: Modifier = Modifier,
) {
  Scaffold(
    modifier = modifier.fillMaxSize(),
    containerColor = MaterialTheme.colorScheme.background,
    contentWindowInsets = WindowInsets.safeDrawing,
  ) { innerPadding ->
    LazyColumn(
      modifier = Modifier.fillMaxSize().padding(innerPadding),
      contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 32.dp),
      verticalArrangement = Arrangement.spacedBy(18.dp),
    ) {
      item { DashboardHeader(dashboard.userName) }
      item { BalanceCard(dashboard) }
      item { CashFlowRow(dashboard) }
      item { WeeklySpendingCard(dashboard.weeklySpending) }
      item { SavingsCard(dashboard.savingsCurrent, dashboard.savingsGoal) }
      item {
        Text(
          "Monthly budgets",
          style = MaterialTheme.typography.titleLarge,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onBackground,
        )
      }
      items(dashboard.budgets, key = BudgetCategory::name) { budget -> BudgetRow(budget) }
      item {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
          Text(
            "Recent transactions",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
          )
          Text("See all", color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.SemiBold)
        }
      }
      items(dashboard.transactions, key = Transaction::id) { transaction -> TransactionRow(transaction) }
    }
  }
}

@Composable
private fun DashboardHeader(userName: String) {
  Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
    Column(modifier = Modifier.weight(1f)) {
      Text("Good morning", color = MaterialTheme.colorScheme.onSurfaceVariant)
      Text(
        userName,
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground,
      )
    }
    Image(
      painter = painterResource(R.drawable.profile_maya),
      contentDescription = "$userName profile photo",
      modifier = Modifier.size(52.dp).clip(CircleShape),
      contentScale = ContentScale.Crop,
    )
  }
}

@Composable
private fun BalanceCard(dashboard: FinanceDashboard) {
  Card(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(28.dp),
    colors = CardDefaults.cardColors(containerColor = DashboardNavy),
  ) {
    Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(7.dp)) {
      Text("Total balance", color = OnDashboardNavy.copy(alpha = 0.72f))
      Text(
        money(dashboard.balance),
        style = MaterialTheme.typography.headlineLarge,
        fontWeight = FontWeight.Bold,
        color = OnDashboardNavy,
      )
      Spacer(Modifier.height(8.dp))
      Surface(shape = RoundedCornerShape(50), color = MintHighlight.copy(alpha = 0.16f)) {
        Text(
          "↑  8.4% this month",
          modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
          color = MintHighlight,
          fontWeight = FontWeight.SemiBold,
        )
      }
    }
  }
}

@Composable
private fun CashFlowRow(dashboard: FinanceDashboard) {
  Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
    MetricCard(
      label = "Income",
      value = dashboard.monthlyIncome,
      symbol = "↑",
      background = MaterialTheme.colorScheme.tertiaryContainer,
      accent = MaterialTheme.colorScheme.tertiary,
      contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
      modifier = Modifier.weight(1f),
    )
    MetricCard(
      label = "Expenses",
      value = dashboard.monthlyExpense,
      symbol = "↓",
      background = MaterialTheme.colorScheme.errorContainer,
      accent = MaterialTheme.colorScheme.error,
      contentColor = MaterialTheme.colorScheme.onErrorContainer,
      modifier = Modifier.weight(1f),
    )
  }
}

@Composable
private fun MetricCard(
  label: String,
  value: Double,
  symbol: String,
  background: Color,
  accent: Color,
  contentColor: Color,
  modifier: Modifier,
) {
  Card(modifier = modifier, shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = background)) {
    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
      Text(symbol, color = accent, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
      Text(label, color = contentColor.copy(alpha = 0.76f))
      Text(money(value, decimals = 0), color = contentColor, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
    }
  }
}

@Composable
private fun WeeklySpendingCard(points: List<SpendingPoint>) {
  Card(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(24.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
  ) {
    Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
      Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Column {
          Text("Weekly spending", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
          Text(
            money(points.sumOf(SpendingPoint::amount), decimals = 0),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
          )
        }
        Text("Last 7 days", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.labelMedium)
      }
      SpendingChart(points)
    }
  }
}

@Composable
private fun SpendingChart(points: List<SpendingPoint>) {
  val maximum = points.maxOfOrNull(SpendingPoint::amount)?.coerceAtLeast(1.0) ?: 1.0
  Row(modifier = Modifier.fillMaxWidth().height(122.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
    points.forEachIndexed { index, point ->
      Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom) {
        Box(
          modifier =
            Modifier.width(22.dp)
              .height((84 * point.amount / maximum).dp.coerceAtLeast(12.dp))
              .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
              .background(
                if (index == points.lastIndex - 1) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.secondary
              ),
        )
        Spacer(Modifier.height(8.dp))
        Text(point.day, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
      }
    }
  }
}

@Composable
private fun SavingsCard(current: Double, goal: Double) {
  val progress = (current / goal).toFloat().coerceIn(0f, 1f)
  Card(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(22.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
  ) {
    Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(11.dp)) {
      Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text("Holiday savings", color = MaterialTheme.colorScheme.onSecondaryContainer, fontWeight = FontWeight.Bold)
        Text("${(progress * 100).toInt()}%", color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)
      }
      LinearProgressIndicator(
        progress = { progress },
        modifier = Modifier.fillMaxWidth().height(9.dp).clip(CircleShape),
        color = MaterialTheme.colorScheme.secondary,
        trackColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.14f),
      )
      Text(
        "${money(current, 0)} of ${money(goal, 0)}",
        color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.76f),
        style = MaterialTheme.typography.bodySmall,
      )
    }
  }
}

@Composable
private fun BudgetRow(budget: BudgetCategory) {
  val progress = (budget.spent / budget.limit).toFloat().coerceIn(0f, 1f)
  Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
      Text(budget.name, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.SemiBold)
      Text("${money(budget.spent, 0)} / ${money(budget.limit, 0)}", color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
    LinearProgressIndicator(
      progress = { progress },
      modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape),
      color = if (progress > 0.75f) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.tertiary,
      trackColor = MaterialTheme.colorScheme.surfaceContainerHighest,
    )
  }
}

@Composable
private fun TransactionRow(transaction: Transaction) {
  Row(modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp), verticalAlignment = Alignment.CenterVertically) {
    val isIncome = transaction.type == TransactionType.INCOME
    val containerColor = if (isIncome) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.errorContainer
    val accentColor = if (isIncome) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.error
    Box(
      modifier = Modifier.size(46.dp).clip(RoundedCornerShape(15.dp)).background(containerColor),
      contentAlignment = Alignment.Center,
    ) {
      Text(if (isIncome) "+" else "−", color = accentColor, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
    }
    Spacer(Modifier.width(12.dp))
    Column(modifier = Modifier.weight(1f)) {
      Text(transaction.title, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.SemiBold)
      Text("${transaction.category}  •  ${transaction.date}", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
    }
    Text(
      text = if (isIncome) "+${money(transaction.amount)}" else "−${money(transaction.amount)}",
      color = accentColor,
      fontWeight = FontWeight.Bold,
    )
  }
}

private fun money(value: Double, decimals: Int = 2): String {
  val pattern = if (decimals == 0) "$#,##0" else "$#,##0.00"
  return DecimalFormat(pattern).format(value)
}

@Preview(showBackground = true)
@Composable
private fun DashboardScreenPreview() {
  FinanceDashboardTheme { DashboardScreen(MockFinanceData.dashboard) }
}
