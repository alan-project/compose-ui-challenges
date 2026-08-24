package com.example.burgerbuilder.ui.burgerbuilder

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.text.style.TextOverflow
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
  var activeSheet by remember { mutableStateOf<CustomizerSheet?>(null) }

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

  BurgerCustomizationPage(
    burger = burger,
    isDoublePatty = isDoublePatty,
    selectedVegetableIds = selectedVegetableIds,
    selectedExtraIds = selectedExtraIds,
    quantity = quantity,
    specialInstructions = specialInstructions,
    totalPrice = totalPrice,
    onDoublePattyChange = { isDoublePatty = it },
    onVegetablesClick = { activeSheet = CustomizerSheet.Vegetables },
    onExtrasClick = { activeSheet = CustomizerSheet.Extras },
    onQuantityChange = { quantity = it.coerceIn(1, 10) },
    onSpecialInstructionsChange = { specialInstructions = it },
    onAddToOrder = {},
    modifier = modifier,
  )

  activeSheet?.let { sheet ->
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val closeSheet: () -> Unit = {
      scope.launch { sheetState.hide() }.invokeOnCompletion {
        if (!sheetState.isVisible) activeSheet = null
      }
    }

    ModalBottomSheet(
      onDismissRequest = { activeSheet = null },
      sheetState = sheetState,
      containerColor = MaterialTheme.colorScheme.surface,
    ) {
      when (sheet) {
        CustomizerSheet.Vegetables ->
          OptionPickerSheet(
            title = "Choose vegetables",
            subtitle = "Remove anything you do not want, or add some heat.",
            options = burger.vegetables,
            selectedOptionIds = selectedVegetableIds,
            onOptionToggle = { optionId ->
              selectedVegetableIds = selectedVegetableIds.toggled(optionId)
            },
            onDone = closeSheet,
          )

        CustomizerSheet.Extras ->
          OptionPickerSheet(
            title = "Add extras",
            subtitle = "A little extra cheddar has never hurt a burger.",
            options = burger.extras,
            selectedOptionIds = selectedExtraIds,
            onOptionToggle = { optionId ->
              selectedExtraIds = selectedExtraIds.toggled(optionId)
            },
            onDone = closeSheet,
          )
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BurgerCustomizationPage(
  burger: Burger,
  isDoublePatty: Boolean,
  selectedVegetableIds: Collection<String>,
  selectedExtraIds: Collection<String>,
  quantity: Int,
  specialInstructions: String,
  totalPrice: Double,
  onDoublePattyChange: (Boolean) -> Unit,
  onVegetablesClick: () -> Unit,
  onExtrasClick: () -> Unit,
  onQuantityChange: (Int) -> Unit,
  onSpecialInstructionsChange: (String) -> Unit,
  onAddToOrder: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Scaffold(
    modifier = modifier.fillMaxSize(),
    containerColor = MaterialTheme.colorScheme.background,
    contentWindowInsets = WindowInsets.safeDrawing,
    topBar = {
      TopAppBar(
        title = {
          Text(
            text = "Order",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold,
          )
        },
        colors =
          TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
          ),
      )
    },
    bottomBar = {
      Surface(
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 4.dp,
      ) {
        Button(
          onClick = onAddToOrder,
          modifier =
            Modifier
              .fillMaxWidth()
              .navigationBarsPadding()
              .padding(horizontal = 20.dp, vertical = 14.dp)
              .height(54.dp),
          colors =
            ButtonDefaults.buttonColors(
              containerColor = MaterialTheme.colorScheme.secondaryContainer,
              contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            ),
          shape = RoundedCornerShape(6.dp),
        ) {
          Text(
            text = "Add to order  •  ${formatPrice(totalPrice)}",
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
          .verticalScroll(rememberScrollState())
          .imePadding()
          .padding(horizontal = 20.dp, vertical = 12.dp),
      verticalArrangement = Arrangement.spacedBy(18.dp),
    ) {
      CompactBurgerHeader(burger = burger)

      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        SectionTitle("Patty")
        Column {
          RadioOptionRow(
            label = "Single patty",
            price = "Included",
            selected = !isDoublePatty,
            onClick = { onDoublePattyChange(false) },
          )
          HorizontalDivider(
            modifier = Modifier.padding(start = 52.dp),
            color = MaterialTheme.colorScheme.outlineVariant,
          )
          RadioOptionRow(
            label = "Double patty",
            price = "+${formatPrice(burger.doublePattyPrice)}",
            selected = isDoublePatty,
            onClick = { onDoublePattyChange(true) },
          )
          HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        }
      }

      Column {
        OptionCategoryRow(
          title = "Vegetables",
          summary = optionSummary(burger.vegetables, selectedVegetableIds),
          onClick = onVegetablesClick,
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        OptionCategoryRow(
          title = "Extras",
          summary = optionSummary(burger.extras, selectedExtraIds),
          onClick = onExtrasClick,
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
      }

      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        SectionTitle("Quantity")
        QuantityStepper(
          quantity = quantity,
          onQuantityChange = onQuantityChange,
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
      }

      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        SectionTitle("Special instructions")
        OutlinedTextField(
          value = specialInstructions,
          onValueChange = onSpecialInstructionsChange,
          modifier = Modifier.fillMaxWidth(),
          placeholder = { Text("Sauce on the side, no salt…") },
          minLines = 2,
          maxLines = 4,
          shape = RoundedCornerShape(8.dp),
        )
      }

      Spacer(Modifier.height(8.dp))
    }
  }
}

@Composable
private fun CompactBurgerHeader(
  burger: Burger,
  modifier: Modifier = Modifier,
) {
  Column(modifier = modifier.fillMaxWidth()) {
    Row(
      modifier = Modifier.padding(vertical = 8.dp),
      verticalAlignment = Alignment.CenterVertically,
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
            .size(116.dp)
            .clip(RoundedCornerShape(8.dp)),
        placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceContainerHigh),
        error = ColorPainter(MaterialTheme.colorScheme.surfaceContainerHigh),
        contentScale = ContentScale.Crop,
      )
      Spacer(Modifier.width(14.dp))
      Column(
        modifier = Modifier.weight(1f),
        verticalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        Surface(
          color = MaterialTheme.colorScheme.secondaryContainer,
          contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
          shape = RoundedCornerShape(4.dp),
        ) {
          Text(
            text = "Made to order",
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
          )
        }
        Text(
          text = burger.name,
          style = MaterialTheme.typography.titleLarge,
          fontWeight = FontWeight.ExtraBold,
          maxLines = 2,
          overflow = TextOverflow.Ellipsis,
        )
        Text(
          text = "From ${formatPrice(burger.basePrice)}",
          color = MaterialTheme.colorScheme.primary,
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
        )
      }
    }
    HorizontalDivider(
      thickness = 6.dp,
      color = MaterialTheme.colorScheme.surfaceContainer,
    )
  }
}

@Composable
private fun OptionCategoryRow(
  title: String,
  summary: String,
  onClick: () -> Unit,
) {
  Row(
    modifier =
      Modifier
        .fillMaxWidth()
        .clickable(onClick = onClick)
        .padding(horizontal = 4.dp, vertical = 18.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Spacer(
      modifier =
        Modifier
          .width(4.dp)
          .height(38.dp)
          .background(
            color = MaterialTheme.colorScheme.secondaryContainer,
            shape = RoundedCornerShape(2.dp),
          ),
    )
    Spacer(Modifier.width(14.dp))
    Column(
      modifier = Modifier.weight(1f),
      verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
      Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.ExtraBold,
      )
      Text(
        text = summary,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        style = MaterialTheme.typography.bodyMedium,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
      )
    }
    Text(
      text = "›",
      color = MaterialTheme.colorScheme.onSurface,
      style = MaterialTheme.typography.headlineSmall,
      fontWeight = FontWeight.Bold,
    )
  }
}

@Composable
private fun OptionPickerSheet(
  title: String,
  subtitle: String,
  options: List<BurgerOption>,
  selectedOptionIds: Collection<String>,
  onOptionToggle: (String) -> Unit,
  onDone: () -> Unit,
) {
  Column(
    modifier =
      Modifier
        .fillMaxWidth()
        .navigationBarsPadding()
        .padding(horizontal = 20.dp)
        .padding(bottom = 20.dp),
  ) {
    Text(
      text = title,
      style = MaterialTheme.typography.headlineSmall,
      fontWeight = FontWeight.ExtraBold,
    )
    Spacer(Modifier.height(6.dp))
    Text(
      text = subtitle,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
      style = MaterialTheme.typography.bodyMedium,
    )
    Spacer(Modifier.height(18.dp))
    options.forEachIndexed { index, option ->
      CheckboxOptionRow(
        label = option.name,
        price =
          if (option.additionalPrice == 0.0) {
            "Free"
          } else {
            "+${formatPrice(option.additionalPrice)}"
          },
        checked = option.id in selectedOptionIds,
        onClick = { onOptionToggle(option.id) },
      )
      if (index < options.lastIndex) {
        HorizontalDivider(
          modifier = Modifier.padding(start = 52.dp),
          color = MaterialTheme.colorScheme.outlineVariant,
        )
      }
    }
    Button(
      onClick = onDone,
      modifier =
        Modifier
          .fillMaxWidth()
          .padding(top = 18.dp)
          .height(52.dp),
      colors =
        ButtonDefaults.buttonColors(
          containerColor = MaterialTheme.colorScheme.secondaryContainer,
          contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        ),
      shape = RoundedCornerShape(6.dp),
    ) {
      Text(
        text = "Done  •  ${selectedOptionIds.size} selected",
        fontWeight = FontWeight.Bold,
      )
    }
  }
}

@Composable
private fun SectionTitle(title: String) {
  Text(
    text = title,
    style = MaterialTheme.typography.titleLarge,
    fontWeight = FontWeight.ExtraBold,
  )
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
        .padding(vertical = 12.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    RadioButton(
      selected = selected,
      onClick = null,
      colors =
        RadioButtonDefaults.colors(
          selectedColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
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
        .padding(vertical = 12.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Checkbox(
      checked = checked,
      onCheckedChange = null,
      colors =
        CheckboxDefaults.colors(
          checkedColor = MaterialTheme.colorScheme.secondaryContainer,
          checkmarkColor = MaterialTheme.colorScheme.onSecondaryContainer,
        ),
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
        .padding(vertical = 10.dp),
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
        .clip(RoundedCornerShape(4.dp))
        .background(
          if (enabled) {
            MaterialTheme.colorScheme.secondaryContainer
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
          MaterialTheme.colorScheme.onSecondaryContainer
        } else {
          MaterialTheme.colorScheme.onSurfaceVariant
        },
      style = MaterialTheme.typography.titleLarge,
      fontWeight = FontWeight.Bold,
    )
  }
}

private enum class CustomizerSheet {
  Vegetables,
  Extras,
}

private fun ArrayList<String>.toggled(optionId: String): ArrayList<String> =
  ArrayList(this).apply {
    if (!remove(optionId)) add(optionId)
  }

private fun optionSummary(
  options: List<BurgerOption>,
  selectedOptionIds: Collection<String>,
): String {
  val selectedNames =
    options
      .filter { option -> option.id in selectedOptionIds }
      .map(BurgerOption::name)

  return when {
    selectedNames.isEmpty() -> "None selected"
    selectedNames.size <= 2 -> selectedNames.joinToString()
    else -> "${selectedNames.take(2).joinToString()} +${selectedNames.size - 2}"
  }
}

private fun formatPrice(price: Double): String = "$${String.format(Locale.US, "%.2f", price)}"

@Preview(showBackground = true)
@Composable
private fun BurgerBuilderPreview() {
  BurgerBuilderTheme {
    BurgerBuilderScreen(burger = MockBurger.item)
  }
}
