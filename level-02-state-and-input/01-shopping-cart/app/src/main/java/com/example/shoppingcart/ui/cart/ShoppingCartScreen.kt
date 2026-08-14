package com.example.shoppingcart.ui.cart

import android.content.res.Configuration
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
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.example.shoppingcart.R
import com.example.shoppingcart.data.mock.MockCartItems
import com.example.shoppingcart.data.model.CartItem
import com.example.shoppingcart.data.model.CartRules
import com.example.shoppingcart.data.model.ProductImage
import com.example.shoppingcart.theme.ShoppingCartTheme
import java.text.NumberFormat
import java.util.Locale
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ShoppingCartRoute(
    viewModel: ShoppingCartViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(viewModel, snackbarHostState, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.events.collectLatest { event ->
                when (event) {
                    is ShoppingCartEvent.ItemRemoved -> {
                        val result =
                            snackbarHostState.showSnackbar(
                                message = "${event.removedItem.item.name} removed",
                                actionLabel = "Undo",
                                withDismissAction = true,
                                duration = SnackbarDuration.Short,
                            )
                        if (result == SnackbarResult.ActionPerformed) {
                            viewModel.onUndoRemove(event.removedItem)
                        } else {
                            viewModel.onEventConsumed(event)
                        }
                    }

                    is ShoppingCartEvent.CheckoutPreview -> {
                        val itemLabel = if (event.selectedQuantity == 1) "item" else "items"
                        snackbarHostState.showSnackbar(
                            message = "Checkout preview · ${event.selectedQuantity} $itemLabel selected",
                            withDismissAction = true,
                            duration = SnackbarDuration.Short,
                        )
                        viewModel.onEventConsumed(event)
                    }
                }
            }
        }
    }

    ShoppingCartScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onToggleItemSelection = viewModel::onToggleItemSelection,
        onSelectAll = viewModel::onSelectAll,
        onIncrementQuantity = viewModel::onIncrementQuantity,
        onDecrementQuantity = viewModel::onDecrementQuantity,
        onRemoveItem = viewModel::onRemoveItem,
        onPreviewCheckoutClick = viewModel::onPreviewCheckoutClick,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingCartScreen(
    uiState: ShoppingCartUiState,
    snackbarHostState: SnackbarHostState,
    onToggleItemSelection: (Long) -> Unit,
    onSelectAll: (Boolean) -> Unit,
    onIncrementQuantity: (Long) -> Unit,
    onDecrementQuantity: (Long) -> Unit,
    onRemoveItem: (Long) -> Unit,
    onPreviewCheckoutClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing,
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { MorrowTopBar(totalQuantity = uiState.totalQuantity) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = {
            CheckoutPanel(
                uiState = uiState,
                onPreviewCheckoutClick = onPreviewCheckoutClick,
            )
        },
    ) { innerPadding ->
        val layoutDirection = LocalLayoutDirection.current
        LazyColumn(
            modifier =
                Modifier.fillMaxSize()
                    .consumeWindowInsets(innerPadding),
            contentPadding =
                PaddingValues(
                    start = innerPadding.calculateStartPadding(layoutDirection) + 16.dp,
                    top = innerPadding.calculateTopPadding() + 8.dp,
                    end = innerPadding.calculateEndPadding(layoutDirection) + 16.dp,
                    bottom = innerPadding.calculateBottomPadding() + 24.dp,
                ),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item(key = "heading") {
                CartHeading(totalQuantity = uiState.totalQuantity)
            }

            if (uiState.items.isNotEmpty()) {
                item(key = "select-all") {
                    SelectAllRow(
                        selectedLineCount = uiState.selectedLineCount,
                        totalLineCount = uiState.items.size,
                        allSelected = uiState.allSelected,
                        onSelectAll = onSelectAll,
                    )
                }

                items(
                    items = uiState.items,
                    key = CartItem::id,
                ) { item ->
                    CartItemCard(
                        item = item,
                        onToggleSelection = { onToggleItemSelection(item.id) },
                        onIncrementQuantity = { onIncrementQuantity(item.id) },
                        onDecrementQuantity = { onDecrementQuantity(item.id) },
                        onRemove = { onRemoveItem(item.id) },
                    )
                }

                item(key = "checkout-note") {
                    CheckoutNote()
                }
            } else {
                item(key = "empty-cart") {
                    EmptyCart()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MorrowTopBar(
    totalQuantity: Int,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Surface(
                    modifier = Modifier.size(36.dp),
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary,
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "M",
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Black,
                        )
                    }
                }
                Column {
                    Text(
                        text = "MORROW",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.6.sp,
                    )
                    Text(
                        text = "Everyday goods, considered",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.labelMedium,
                    )
                }
            }
        },
        actions = {
            Surface(
                modifier =
                    Modifier.padding(end = 16.dp)
                        .semantics {
                            contentDescription = "$totalQuantity items in your bag"
                        },
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primaryContainer,
            ) {
                Text(
                    text = totalQuantity.toString(),
                    modifier = Modifier.padding(horizontal = 13.dp, vertical = 8.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        },
    )
}

@Composable
private fun CartHeading(
    totalQuantity: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(top = 8.dp, bottom = 2.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        Text(
            text = "YOUR BAG",
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.4.sp,
        )
        Text(
            text = if (totalQuantity == 0) "Room for something good" else "Good choices, all together",
            style = MaterialTheme.typography.headlineLarge,
        )
        Text(
            text =
                if (totalQuantity == 0) {
                    "Your considered finds will wait here."
                } else {
                    "$totalQuantity ${if (totalQuantity == 1) "piece" else "pieces"} in your bag · Edit anytime"
                },
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun SelectAllRow(
    selectedLineCount: Int,
    totalLineCount: Int,
    allSelected: Boolean,
    onSelectAll: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val toggleState =
        when {
            allSelected -> ToggleableState.On
            selectedLineCount > 0 -> ToggleableState.Indeterminate
            else -> ToggleableState.Off
        }

    Row(
        modifier =
            modifier.fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .toggleable(
                    value = allSelected,
                    role = Role.Checkbox,
                    onValueChange = onSelectAll,
                )
                .semantics {
                    stateDescription = "$selectedLineCount of $totalLineCount products selected"
                }
                .padding(horizontal = 4.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TriStateCheckbox(
            state = toggleState,
            onClick = null,
            colors =
                CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary,
                    checkmarkColor = MaterialTheme.colorScheme.onPrimary,
                ),
        )
        Text(
            text = "Select all",
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "$selectedLineCount / $totalLineCount selected",
            modifier = Modifier.padding(end = 10.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelMedium,
        )
    }
}

@Composable
private fun CartItemCard(
    item: CartItem,
    onToggleSelection: () -> Unit,
    onIncrementQuantity: () -> Unit,
    onDecrementQuantity: () -> Unit,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val borderColor =
        if (item.isSelected) {
            MaterialTheme.colorScheme.primary.copy(alpha = 0.42f)
        } else {
            MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.7f)
        }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        border = BorderStroke(1.dp, borderColor),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Box(
                modifier =
                    Modifier.size(width = 104.dp, height = 144.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(productTint(item.image)),
            ) {
                Image(
                    painter = painterResource(item.image.drawableRes()),
                    contentDescription = "${item.name}, ${item.variant}",
                    modifier =
                        Modifier.fillMaxSize()
                            .alpha(if (item.isSelected) 1f else 0.72f),
                    contentScale = ContentScale.Crop,
                )
                Surface(
                    modifier = Modifier.align(Alignment.TopStart).padding(4.dp),
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.92f),
                ) {
                    Checkbox(
                        checked = item.isSelected,
                        onCheckedChange = { onToggleSelection() },
                        modifier =
                            Modifier.semantics {
                                contentDescription =
                                    if (item.isSelected) {
                                        "Deselect ${item.name}"
                                    } else {
                                        "Select ${item.name}"
                                    }
                            },
                        colors =
                            CheckboxDefaults.colors(
                                checkedColor = MaterialTheme.colorScheme.primary,
                                checkmarkColor = MaterialTheme.colorScheme.onPrimary,
                            ),
                    )
                }
            }

            Column(
                modifier = Modifier.weight(1f).heightIn(min = 144.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top,
                ) {
                    Text(
                        text = item.name,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                    IconButton(
                        onClick = onRemove,
                        modifier =
                            Modifier.semantics {
                                contentDescription = "Remove ${item.name} from bag"
                            },
                    ) {
                        Text(
                            text = "×",
                            color = MaterialTheme.colorScheme.secondary,
                            style = MaterialTheme.typography.headlineSmall,
                        )
                    }
                }
                Text(
                    text = item.variant,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(7.dp))
                Text(
                    text = item.unitPriceCents.asCurrency(),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.weight(1f))
                QuantityStepper(
                    itemName = item.name,
                    quantity = item.quantity,
                    onIncrement = onIncrementQuantity,
                    onDecrement = onDecrementQuantity,
                )
            }
        }
    }
}

@Composable
private fun QuantityStepper(
    itemName: String,
    quantity: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.58f),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = onDecrement,
                enabled = quantity > CartRules.MIN_QUANTITY,
                modifier =
                    Modifier.semantics {
                        contentDescription = "Decrease quantity of $itemName"
                    },
            ) {
                Text(
                    text = "−",
                    style = MaterialTheme.typography.titleLarge,
                )
            }
            Text(
                text = quantity.toString(),
                modifier =
                    Modifier.width(24.dp)
                        .semantics { contentDescription = "Quantity $quantity" },
                style = MaterialTheme.typography.labelLarge,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            )
            IconButton(
                onClick = onIncrement,
                enabled = quantity < CartRules.MAX_QUANTITY,
                modifier =
                    Modifier.semantics {
                        contentDescription = "Increase quantity of $itemName"
                    },
            ) {
                Text(
                    text = "+",
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        }
    }
}

@Composable
private fun CheckoutPanel(
    uiState: ShoppingCartUiState,
    onPreviewCheckoutClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 5.dp,
        shadowElevation = 12.dp,
    ) {
        Column(
            modifier =
                Modifier.navigationBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(9.dp),
        ) {
            FreeShippingStatus(uiState = uiState)
            SummaryRow(label = "Subtotal", value = uiState.subtotalCents.asCurrency())
            SummaryRow(
                label = "Delivery",
                value =
                    when {
                        !uiState.checkoutEnabled -> "—"
                        uiState.shippingCents == 0L -> "Free"
                        else -> uiState.shippingCents.asCurrency()
                    },
                emphasized = uiState.checkoutEnabled && uiState.shippingCents == 0L,
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text(
                        text = "Total",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        text = "Tax calculated next",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.labelMedium,
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = uiState.totalCents.asCurrency(),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                )
            }
            Button(
                onClick = onPreviewCheckoutClick,
                enabled = uiState.checkoutEnabled,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(18.dp),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
            ) {
                Text(
                    text =
                        if (uiState.checkoutEnabled) {
                            "Preview checkout · ${uiState.selectedQuantity} ${if (uiState.selectedQuantity == 1) "item" else "items"}"
                        } else {
                            "Select items to continue"
                        },
                )
            }
        }
    }
}

@Composable
private fun FreeShippingStatus(
    uiState: ShoppingCartUiState,
    modifier: Modifier = Modifier,
) {
    val progress =
        (uiState.subtotalCents.toFloat() / CartRules.FREE_SHIPPING_THRESHOLD_CENTS.toFloat())
            .coerceIn(0f, 1f)
    val message =
        when {
            !uiState.checkoutEnabled -> "Select an item to build your order"
            uiState.remainingForFreeShippingCents == 0L -> "Free delivery unlocked"
            else -> "${uiState.remainingForFreeShippingCents.asCurrency()} away from free delivery"
        }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = message,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${(progress * 100).toInt()}%",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.labelMedium,
            )
        }
        Box(
            modifier =
                Modifier.fillMaxWidth()
                    .height(6.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
        ) {
            Box(
                modifier =
                    Modifier.fillMaxWidth(progress)
                        .fillMaxHeight()
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondary),
            )
        }
    }
}

@Composable
private fun SummaryRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    emphasized: Boolean = false,
) {
    Row(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = value,
            color =
                if (emphasized) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurface
                },
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (emphasized) FontWeight.Bold else FontWeight.Medium,
        )
    }
}

@Composable
private fun CheckoutNote(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.62f),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Surface(
                modifier = Modifier.size(38.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary,
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "✓",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            }
            Column {
                Text(
                    text = "Easy, secure checkout",
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = "30-day returns · Carbon-neutral delivery",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Composable
private fun EmptyCart(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth().padding(vertical = 48.dp, horizontal = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Surface(
            modifier = Modifier.size(88.dp),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer,
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = "M",
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Black,
                )
            }
        }
        Text(
            text = "Your bag is taking a breather",
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = "Add a considered essential and it will appear right here.",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
        )
    }
}

private fun ProductImage.drawableRes(): Int =
    when (this) {
        ProductImage.HEADPHONES -> R.drawable.product_headphones
        ProductImage.KEYBOARD -> R.drawable.product_keyboard
        ProductImage.SMARTWATCH -> R.drawable.product_smartwatch
        ProductImage.BACKPACK -> R.drawable.product_backpack
    }

private fun productTint(image: ProductImage): Color =
    when (image) {
        ProductImage.HEADPHONES -> Color(0xFFFFD9CC)
        ProductImage.KEYBOARD -> Color(0xFFCDEDE1)
        ProductImage.SMARTWATCH -> Color(0xFFFFE8AF)
        ProductImage.BACKPACK -> Color(0xFFD9E3C3)
    }

private fun Long.asCurrency(): String =
    NumberFormat.getCurrencyInstance(Locale.US).format(this / 100.0)

@Preview(name = "Shopping cart · Light", showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun ShoppingCartScreenLightPreview() {
    ShoppingCartTheme(darkTheme = false) {
        ShoppingCartScreenPreviewContent()
    }
}

@Preview(
    name = "Shopping cart · Dark",
    showBackground = true,
    widthDp = 390,
    heightDp = 844,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun ShoppingCartScreenDarkPreview() {
    ShoppingCartTheme(darkTheme = true) {
        ShoppingCartScreenPreviewContent()
    }
}

@Composable
private fun ShoppingCartScreenPreviewContent() {
    ShoppingCartScreen(
        uiState = createShoppingCartUiState(MockCartItems.items),
        snackbarHostState = remember { SnackbarHostState() },
        onToggleItemSelection = {},
        onSelectAll = {},
        onIncrementQuantity = {},
        onDecrementQuantity = {},
        onRemoveItem = {},
        onPreviewCheckoutClick = {},
    )
}
