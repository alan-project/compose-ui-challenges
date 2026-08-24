package com.example.burgerbuilder.ui.burgerbuilder

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.burgerbuilder.data.mock.MockBurger
import com.example.burgerbuilder.data.model.Burger
import com.example.burgerbuilder.data.model.BurgerOption
import com.example.burgerbuilder.data.model.calculateBurgerTotal
import com.example.burgerbuilder.theme.BurgerBuilderTheme
import java.util.Locale
import kotlinx.coroutines.launch

@Composable
fun BurgerBuilderRoute(
  viewModel: BurgerBuilderViewModel,
  modifier: Modifier = Modifier,
) {
  val burger by viewModel.burger.collectAsStateWithLifecycle()

  BurgerBuilderScreen(
    burger = burger,
    modifier = modifier,
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BurgerBuilderScreen(
  burger: Burger,
  modifier: Modifier = Modifier,
) {
  var showCustomizer by remember { mutableStateOf(false) }

  var isDoublePatty by rememberSaveable(burger.id) { mutableStateOf(false) }
  var selectedVegetableIds by
    rememberSaveable(burger.id) {
      mutableStateOf(
        ArrayList(
          burger.vegetables
            .filter(BurgerOption::selectedByDefault)
            .map(BurgerOption::id),
        ),
      )
    }
  var selectedExtraIds by
    rememberSaveable(burger.id) {
      mutableStateOf(ArrayList<String>())
    }
  var quantity by rememberSaveable(burger.id) { mutableStateOf(1) }
  var specialInstructions by rememberSaveable(burger.id) { mutableStateOf("") }

  val totalPrice =
    calculateBurgerTotal(
      burger = burger,
      isDoublePatty = isDoublePatty,
      selectedExtraIds = selectedExtraIds,
      quantity = quantity,
    )

  BurgerProductPage(
    burger = burger,
    quantity = quantity,
    totalPrice = totalPrice,
    onCustomizeClick = { showCustomizer = true },
    modifier = modifier,
  )

  if (showCustomizer) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
      onDismissRequest = { showCustomizer = false },
      sheetState = sheetState,
    ) {
      BurgerCustomizer(
        burger = burger,
        isDoublePatty = isDoublePatty,
        selectedVegetableIds = selectedVegetableIds,
        selectedExtraIds = selectedExtraIds,
        quantity = quantity,
        specialInstructions = specialInstructions,
        totalPrice = totalPrice,
        onDoublePattyChange = { isDoublePatty = it },
        onVegetableToggle = { optionId ->
          selectedVegetableIds = selectedVegetableIds.toggled(optionId)
        },
        onExtraToggle = { optionId ->
          selectedExtraIds = selectedExtraIds.toggled(optionId)
        },
        onQuantityChange = { quantity = it.coerceIn(1, 10) },
        onSpecialInstructionsChange = { specialInstructions = it },
        onAddToOrder = {
          scope.launch { sheetState.hide() }.invokeOnCompletion {
            if (!sheetState.isVisible) showCustomizer = false
          }
        },
      )
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BurgerProductPage(
  burger: Burger,
  quantity: Int,
  totalPrice: Double,
  onCustomizeClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Scaffold(
    modifier = modifier.fillMaxSize(),
    containerColor = MaterialTheme.colorScheme.background,
    contentWindowInsets = WindowInsets.safeDrawing,
    topBar = {
      CenterAlignedTopAppBar(
        title = {
          Text(
            text = "Burger Builder",
            fontWeight = FontWeight.Bold,
          )
        },
        colors =
          TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
          ),
      )
    },
    bottomBar = {
      Surface(
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 12.dp,
      ) {
        Button(
          onClick = onCustomizeClick,
          modifier =
            Modifier
              .fillMaxWidth()
              .navigationBarsPadding()
              .padding(horizontal = 20.dp, vertical = 14.dp)
              .height(54.dp),
          shape = RoundedCornerShape(18.dp),
        ) {
          Text(
            text = "Customize order  •  ${formatPrice(totalPrice)}",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
          )
        }
      }
    },
  ) { innerPadding ->
    Column(
      modifier =
        Modifier
          .fillMaxSize()
          .padding(innerPadding)
          .verticalScroll(rememberScrollState()),
    ) {
      AsyncImage(
        model =
          ImageRequest.Builder(LocalContext.current)
            .data(burger.imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = burger.name,
        modifier =
          Modifier
            .fillMaxWidth()
            .aspectRatio(1.3f),
        placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceContainerHigh),
        error = ColorPainter(MaterialTheme.colorScheme.surfaceContainerHigh),
        contentScale = ContentScale.Crop,
      )

      Column(
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 22.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
      ) {
        Surface(
          color = MaterialTheme.colorScheme.secondaryContainer,
          contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
          shape = RoundedCornerShape(50),
        ) {
          Text(
            text = "MADE TO ORDER",
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
          )
        }
        Text(
          text = burger.name,
          style = MaterialTheme.typography.headlineMedium,
          fontWeight = FontWeight.ExtraBold,
        )
        Text(
          text = burger.description,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          style = MaterialTheme.typography.bodyLarge,
        )
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically,
        ) {
          Text(
            text = "From ${formatPrice(burger.basePrice)}",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
          )
          Surface(
            color = MaterialTheme.colorScheme.surfaceContainer,
            shape = RoundedCornerShape(12.dp),
          ) {
            Text(
              text = if (quantity == 1) "1 burger" else "$quantity burgers",
              modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
              style = MaterialTheme.typography.labelLarge,
            )
          }
        }
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        Text(
          text = "Built fresh with cheddar, house sauce, vegetables, and a toasted brioche bun.",
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          style = MaterialTheme.typography.bodyMedium,
        )
      }
    }
  }
}

@Composable
private fun BurgerCustomizer(
  burger: Burger,
  isDoublePatty: Boolean,
  selectedVegetableIds: Collection<String>,
  selectedExtraIds: Collection<String>,
  quantity: Int,
  specialInstructions: String,
  totalPrice: Double,
  onDoublePattyChange: (Boolean) -> Unit,
  onVegetableToggle: (String) -> Unit,
  onExtraToggle: (String) -> Unit,
  onQuantityChange: (Int) -> Unit,
  onSpecialInstructionsChange: (String) -> Unit,
  onAddToOrder: () -> Unit,
) {
  Column(
    modifier =
      Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())
        .imePadding()
        .padding(horizontal = 20.dp)
        .padding(bottom = 24.dp),
  ) {
    Text(
      text = "Customize your burger",
      style = MaterialTheme.typography.headlineSmall,
      fontWeight = FontWeight.ExtraBold,
    )
    Spacer(Modifier.height(6.dp))
    Text(
      text = burger.name,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
      style = MaterialTheme.typography.bodyLarge,
    )

    CustomizerSection(title = "Patty") {
      RadioOptionRow(
        label = "Single patty",
        price = "Included",
        selected = !isDoublePatty,
        onClick = { onDoublePattyChange(false) },
      )
      RadioOptionRow(
        label = "Double patty",
        price = "+${formatPrice(burger.doublePattyPrice)}",
        selected = isDoublePatty,
        onClick = { onDoublePattyChange(true) },
      )
    }

    CustomizerSection(title = "Vegetables") {
      burger.vegetables.forEach { option ->
        CheckboxOptionRow(
          label = option.name,
          price = "Free",
          checked = option.id in selectedVegetableIds,
          onClick = { onVegetableToggle(option.id) },
        )
      }
    }

    CustomizerSection(title = "Extras") {
      burger.extras.forEach { option ->
        CheckboxOptionRow(
          label = option.name,
          price = "+${formatPrice(option.additionalPrice)}",
          checked = option.id in selectedExtraIds,
          onClick = { onExtraToggle(option.id) },
        )
      }
    }

    CustomizerSection(title = "Quantity") {
      QuantityStepper(
        quantity = quantity,
        onQuantityChange = onQuantityChange,
      )
    }

    Text(
      text = "Special instructions",
      modifier = Modifier.padding(top = 24.dp, bottom = 10.dp),
      style = MaterialTheme.typography.titleMedium,
      fontWeight = FontWeight.Bold,
    )
    OutlinedTextField(
      value = specialInstructions,
      onValueChange = onSpecialInstructionsChange,
      modifier = Modifier.fillMaxWidth(),
      placeholder = { Text("Sauce on the side, no salt…") },
      minLines = 3,
      maxLines = 4,
      shape = RoundedCornerShape(16.dp),
    )

    Button(
      onClick = onAddToOrder,
      modifier =
        Modifier
          .fillMaxWidth()
          .padding(top = 24.dp)
          .height(54.dp),
      shape = RoundedCornerShape(18.dp),
    ) {
      Text(
        text = "Add to order  •  ${formatPrice(totalPrice)}",
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Bold,
      )
    }
  }
}

@Composable
private fun CustomizerSection(
  title: String,
  content: @Composable () -> Unit,
) {
  Text(
    text = title,
    modifier = Modifier.padding(top = 24.dp, bottom = 10.dp),
    style = MaterialTheme.typography.titleMedium,
    fontWeight = FontWeight.Bold,
  )
  Card(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(18.dp),
    colors =
      CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
      ),
    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
  ) {
    Column { content() }
  }
}

@Composable
private fun RadioOptionRow(
  label: String,
  price: String,
  selected: Boolean,
  onClick: () -> Unit,
) {
  Row(
    modifier =
      Modifier
        .fillMaxWidth()
        .clickable(onClick = onClick)
        .padding(horizontal = 12.dp, vertical = 10.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    RadioButton(
      selected = selected,
      onClick = null,
    )
    Spacer(Modifier.width(8.dp))
    Text(
      text = label,
      modifier = Modifier.weight(1f),
      style = MaterialTheme.typography.bodyLarge,
      fontWeight = FontWeight.Medium,
    )
    Text(
      text = price,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
      style = MaterialTheme.typography.bodyMedium,
    )
  }
}

@Composable
private fun CheckboxOptionRow(
  label: String,
  price: String,
  checked: Boolean,
  onClick: () -> Unit,
) {
  Row(
    modifier =
      Modifier
        .fillMaxWidth()
        .clickable(onClick = onClick)
        .padding(horizontal = 12.dp, vertical = 10.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Checkbox(
      checked = checked,
      onCheckedChange = null,
    )
    Spacer(Modifier.width(8.dp))
    Text(
      text = label,
      modifier = Modifier.weight(1f),
      style = MaterialTheme.typography.bodyLarge,
      fontWeight = FontWeight.Medium,
    )
    Text(
      text = price,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
      style = MaterialTheme.typography.bodyMedium,
    )
  }
}

@Composable
private fun QuantityStepper(
  quantity: Int,
  onQuantityChange: (Int) -> Unit,
) {
  Row(
    modifier =
      Modifier
        .fillMaxWidth()
        .padding(horizontal = 12.dp, vertical = 10.dp),
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    QuantityButton(
      symbol = "−",
      contentDescription = "Decrease quantity",
      enabled = quantity > 1,
      onClick = { onQuantityChange(quantity - 1) },
    )
    Text(
      text = quantity.toString(),
      modifier = Modifier.width(64.dp),
      textAlign = TextAlign.Center,
      style = MaterialTheme.typography.titleLarge,
      fontWeight = FontWeight.Bold,
    )
    QuantityButton(
      symbol = "+",
      contentDescription = "Increase quantity",
      enabled = quantity < 10,
      onClick = { onQuantityChange(quantity + 1) },
    )
  }
}

@Composable
private fun QuantityButton(
  symbol: String,
  contentDescription: String,
  enabled: Boolean,
  onClick: () -> Unit,
) {
  IconButton(
    onClick = onClick,
    enabled = enabled,
    modifier =
      Modifier
        .size(44.dp)
        .clip(CircleShape)
        .background(
          if (enabled) {
            MaterialTheme.colorScheme.primaryContainer
          } else {
            MaterialTheme.colorScheme.surfaceContainerHighest
          },
        )
        .semantics { this.contentDescription = contentDescription },
  ) {
    Text(
      text = symbol,
      color =
        if (enabled) {
          MaterialTheme.colorScheme.onPrimaryContainer
        } else {
          MaterialTheme.colorScheme.onSurfaceVariant
        },
      style = MaterialTheme.typography.titleLarge,
      fontWeight = FontWeight.Bold,
    )
  }
}

private fun ArrayList<String>.toggled(optionId: String): ArrayList<String> =
  ArrayList(this).apply {
    if (!remove(optionId)) add(optionId)
  }

private fun formatPrice(price: Double): String = "$${String.format(Locale.US, "%.2f", price)}"

@Preview(showBackground = true)
@Composable
private fun BurgerBuilderPreview() {
  BurgerBuilderTheme {
    BurgerBuilderScreen(burger = MockBurger.item)
  }
}
