package com.example.checkout.ui.checkout

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.checkout.R
import com.example.checkout.data.mock.MockCheckoutData
import com.example.checkout.data.model.OrderItem
import com.example.checkout.data.model.PaymentMethod
import com.example.checkout.data.model.PaymentType
import com.example.checkout.data.model.ProductImage
import com.example.checkout.data.model.ShippingOption
import com.example.checkout.theme.CheckoutTheme
import com.example.checkout.theme.Cyan
import com.example.checkout.theme.Golden
import com.example.checkout.theme.HeroViolet
import com.example.checkout.theme.HeroYellow
import com.example.checkout.theme.OnCyan
import com.example.checkout.theme.OnGolden
import com.example.checkout.theme.OnHeroYellow
import com.example.checkout.theme.OnViolet
import com.example.checkout.theme.Violet
import kotlinx.coroutines.launch

@Composable
fun CheckoutRoute(
    viewModel: CheckoutViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CheckoutScreen(
        uiState = uiState,
        onFullNameChanged = viewModel::onFullNameChanged,
        onEmailChanged = viewModel::onEmailChanged,
        onAddressChanged = viewModel::onAddressChanged,
        onCityChanged = viewModel::onCityChanged,
        onPostalCodeChanged = viewModel::onPostalCodeChanged,
        onFieldBlurred = viewModel::onFieldBlurred,
        onShippingSelected = viewModel::onShippingSelected,
        onPaymentSelected = viewModel::onPaymentSelected,
        onPlaceOrder = viewModel::onPlaceOrder,
        onConfirmationDismissed = viewModel::onConfirmationDismissed,
        modifier = modifier,
    )
}

@Composable
fun CheckoutScreen(
    uiState: CheckoutUiState,
    onFullNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onAddressChanged: (String) -> Unit,
    onCityChanged: (String) -> Unit,
    onPostalCodeChanged: (String) -> Unit,
    onFieldBlurred: (CheckoutField) -> Unit,
    onShippingSelected: (String) -> Unit,
    onPaymentSelected: (String) -> Unit,
    onPlaceOrder: () -> Unit,
    onConfirmationDismissed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets.safeDrawing,
    ) { innerPadding ->
        CheckoutContent(
            uiState = uiState,
            innerPadding = innerPadding,
            onFullNameChanged = onFullNameChanged,
            onEmailChanged = onEmailChanged,
            onAddressChanged = onAddressChanged,
            onCityChanged = onCityChanged,
            onPostalCodeChanged = onPostalCodeChanged,
            onFieldBlurred = onFieldBlurred,
            onShippingSelected = onShippingSelected,
            onPaymentSelected = onPaymentSelected,
            onPlaceOrder = {
                focusManager.clearFocus()
                onPlaceOrder()
            },
        )
    }

    if (uiState.isConfirmationDialogVisible) {
        uiState.orderConfirmation?.let { confirmation ->
            OrderSuccessDialog(
                orderNumber = confirmation.orderNumber,
                email = confirmation.email,
                arrivalEstimate = confirmation.arrivalEstimate,
                onDismiss = onConfirmationDismissed,
            )
        }
    }
}

@Composable
private fun CheckoutContent(
    uiState: CheckoutUiState,
    innerPadding: PaddingValues,
    onFullNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onAddressChanged: (String) -> Unit,
    onCityChanged: (String) -> Unit,
    onPostalCodeChanged: (String) -> Unit,
    onFieldBlurred: (CheckoutField) -> Unit,
    onShippingSelected: (String) -> Unit,
    onPaymentSelected: (String) -> Unit,
    onPlaceOrder: () -> Unit,
) {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .widthIn(max = 720.dp)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 18.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp),
        ) {
            CheckoutHeader()
            OrderSummaryCard(uiState.content.items)

            CheckoutSection {
                SectionHeading(
                    number = "01",
                    title = "Contact",
                    subtitle = "Your receipt and delivery updates",
                )
                Spacer(Modifier.height(18.dp))
                CheckoutTextField(
                    value = uiState.fullName,
                    onValueChange = onFullNameChanged,
                    label = "Full name",
                    placeholder = "Maya Chen",
                    error = uiState.visibleError(CheckoutField.FULL_NAME),
                    onFocusLost = { onFieldBlurred(CheckoutField.FULL_NAME) },
                    keyboardOptions =
                        KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words,
                            imeAction = ImeAction.Next,
                        ),
                )
                Spacer(Modifier.height(12.dp))
                CheckoutTextField(
                    value = uiState.email,
                    onValueChange = onEmailChanged,
                    label = "Email address",
                    placeholder = "maya@example.com",
                    error = uiState.visibleError(CheckoutField.EMAIL),
                    onFocusLost = { onFieldBlurred(CheckoutField.EMAIL) },
                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next,
                        ),
                )
            }

            CheckoutSection {
                SectionHeading(
                    number = "02",
                    title = "Delivery",
                    subtitle = "Where should we send your order?",
                )
                Spacer(Modifier.height(18.dp))
                CheckoutTextField(
                    value = uiState.address,
                    onValueChange = onAddressChanged,
                    label = "Street address",
                    placeholder = "88 Harbour Street",
                    error = uiState.visibleError(CheckoutField.ADDRESS),
                    onFocusLost = { onFieldBlurred(CheckoutField.ADDRESS) },
                    keyboardOptions =
                        KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words,
                            imeAction = ImeAction.Next,
                        ),
                )
                Spacer(Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    CheckoutTextField(
                        value = uiState.city,
                        onValueChange = onCityChanged,
                        label = "City",
                        placeholder = "Toronto",
                        error = uiState.visibleError(CheckoutField.CITY),
                        onFocusLost = { onFieldBlurred(CheckoutField.CITY) },
                        keyboardOptions =
                            KeyboardOptions(
                                capitalization = KeyboardCapitalization.Words,
                                imeAction = ImeAction.Next,
                            ),
                        modifier = Modifier.weight(1.25f),
                    )
                    CheckoutTextField(
                        value = uiState.postalCode,
                        onValueChange = onPostalCodeChanged,
                        label = "Postal code",
                        placeholder = "M5J 2N8",
                        error = uiState.visibleError(CheckoutField.POSTAL_CODE),
                        onFocusLost = { onFieldBlurred(CheckoutField.POSTAL_CODE) },
                        keyboardOptions =
                            KeyboardOptions(
                                capitalization = KeyboardCapitalization.Characters,
                                imeAction = ImeAction.Done,
                            ),
                        modifier = Modifier.weight(1f),
                    )
                }

                Spacer(Modifier.height(22.dp))
                Text(
                    text = "Shipping speed",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Spacer(Modifier.height(10.dp))
                Column(
                    modifier = Modifier.selectableGroup(),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    uiState.content.shippingOptions.forEach { option ->
                        ShippingOptionCard(
                            option = option,
                            selected = option.id == uiState.selectedShippingId,
                            onSelected = { onShippingSelected(option.id) },
                        )
                    }
                }
            }

            CheckoutSection {
                SectionHeading(
                    number = "03",
                    title = "Payment",
                    subtitle = "Choose how you would like to pay",
                )
                Spacer(Modifier.height(18.dp))
                Column(
                    modifier = Modifier.selectableGroup(),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    uiState.content.paymentMethods.forEach { payment ->
                        PaymentMethodCard(
                            payment = payment,
                            selected = payment.id == uiState.selectedPaymentId,
                            onSelected = { onPaymentSelected(payment.id) },
                        )
                    }
                }
            }

            OrderTotalCard(uiState)

            if (uiState.showValidationSummary && uiState.errors.isNotEmpty()) {
                ValidationSummary(invalidFields = uiState.errors.keys)
            }

            Button(
                onClick = onPlaceOrder,
                enabled = uiState.orderConfirmation == null,
                modifier = Modifier.fillMaxWidth().height(60.dp),
                shape = RoundedCornerShape(18.dp),
                contentPadding = PaddingValues(horizontal = 22.dp),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
            ) {
                val confirmation = uiState.orderConfirmation
                if (confirmation == null) {
                    Text(
                        text = "Place order",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = formatMoney(uiState.finalTotalCents),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                    )
                } else {
                    Text(
                        text = "Order ${confirmation.orderNumber} confirmed",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(Modifier.weight(1f))
                    Text("✓", style = MaterialTheme.typography.titleLarge)
                }
            }
        }
    }
}

@Composable
private fun ValidationSummary(invalidFields: Set<CheckoutField>) {
    val fieldNames =
        CheckoutField.entries
            .filter(invalidFields::contains)
            .joinToString(separator = ", ") { field -> field.validationLabel }
    val fieldCount = invalidFields.size
    val message =
        "Check $fieldCount ${if (fieldCount == 1) "field" else "fields"} before placing your order: $fieldNames."

    Surface(
        modifier =
            Modifier
                .fillMaxWidth()
                .clearAndSetSemantics {
                    liveRegion = LiveRegionMode.Assertive
                    contentDescription = message
                },
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.errorContainer,
        contentColor = MaterialTheme.colorScheme.onErrorContainer,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.Top,
        ) {
            Surface(
                modifier = Modifier.size(28.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError,
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text("!", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Black)
                }
            }
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = "Check $fieldCount ${if (fieldCount == 1) "field" else "fields"}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = fieldNames,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Composable
private fun CheckoutHeader() {
    val pagerState = rememberPagerState(pageCount = { CheckoutHeroSlides.size })
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        HorizontalPager(
            state = pagerState,
            key = { CheckoutHeroSlides[it].id },
            pageSpacing = 12.dp,
            beyondViewportPageCount = 1,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .semantics {
                        stateDescription =
                            "Checkout highlight ${pagerState.settledPage + 1} of ${CheckoutHeroSlides.size}"
                    },
        ) { page ->
            CheckoutHeroCard(slide = CheckoutHeroSlides[page])
        }

        Row(
            modifier = Modifier.fillMaxWidth().selectableGroup(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CheckoutHeroSlides.forEachIndexed { index, slide ->
                val selected = pagerState.currentPage == index
                val indicatorWidth by
                    animateDpAsState(
                        targetValue = if (selected) 34.dp else 12.dp,
                        label = "hero indicator width",
                    )
                val indicatorColor by
                    animateColorAsState(
                        targetValue =
                            if (selected) {
                                when (index) {
                                    0 -> MaterialTheme.colorScheme.primary
                                    1 -> MaterialTheme.colorScheme.secondary
                                    else -> MaterialTheme.colorScheme.tertiary
                                }
                            } else {
                                MaterialTheme.colorScheme.outline
                            },
                        label = "hero indicator color",
                    )

                Box(
                    modifier =
                        Modifier
                            .size(width = 48.dp, height = 48.dp)
                            .clip(CircleShape)
                            .selectable(
                                selected = selected,
                                onClick = {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(index)
                                    }
                                },
                                role = Role.Tab,
                            ).semantics {
                                contentDescription =
                                    "Checkout highlight ${index + 1} of ${CheckoutHeroSlides.size}: ${slide.title}"
                            },
                    contentAlignment = Alignment.Center,
                ) {
                    Box(
                        modifier =
                            Modifier
                                .width(indicatorWidth)
                                .height(6.dp)
                                .clip(CircleShape)
                                .background(indicatorColor),
                    )
                }
            }
        }
    }
}

@Composable
private fun CheckoutHeroCard(
    slide: CheckoutHeroSlide,
) {
    val colors = checkoutHeroColors(slide.tone)

    Surface(
        modifier =
            Modifier
                .fillMaxWidth()
                .heightIn(min = 250.dp),
        shape = RoundedCornerShape(30.dp),
        color = colors.container,
        contentColor = colors.content,
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Surface(
                    modifier = Modifier.size(40.dp),
                    shape = CircleShape,
                    color = colors.accent,
                    contentColor = colors.onAccent,
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("L", fontWeight = FontWeight.ExtraBold)
                    }
                }
                Spacer(Modifier.width(10.dp))
                Text(
                    text = "LUMA",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.ExtraBold,
                )
                Spacer(Modifier.weight(1f))
                Surface(
                    shape = RoundedCornerShape(100.dp),
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.88f),
                    contentColor = colors.accent,
                ) {
                    Text(
                        text = slide.badge,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = slide.title,
                    modifier = Modifier.semantics { heading() },
                    style = MaterialTheme.typography.displaySmall,
                )
                Text(
                    text = slide.description,
                    modifier = Modifier.widthIn(max = 470.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = colors.content,
                )
            }
        }
    }
}

private data class CheckoutHeroSlide(
    val id: String,
    val title: String,
    val description: String,
    val badge: String,
    val tone: CheckoutHeroTone,
)

private enum class CheckoutHeroTone {
    VIOLET,
    CYAN,
    LEMON,
}

private data class CheckoutHeroColors(
    val container: Color,
    val content: Color,
    val accent: Color,
    val onAccent: Color,
)

private fun checkoutHeroColors(tone: CheckoutHeroTone): CheckoutHeroColors =
    when (tone) {
        CheckoutHeroTone.VIOLET ->
            CheckoutHeroColors(
                container = HeroViolet,
                content = OnViolet,
                accent = Cyan,
                onAccent = OnCyan,
            )
        CheckoutHeroTone.CYAN ->
            CheckoutHeroColors(
                container = Cyan,
                content = OnCyan,
                accent = Golden,
                onAccent = OnGolden,
            )
        CheckoutHeroTone.LEMON ->
            CheckoutHeroColors(
                container = HeroYellow,
                content = OnHeroYellow,
                accent = Violet,
                onAccent = OnViolet,
            )
    }

private val CheckoutHeroSlides =
    listOf(
        CheckoutHeroSlide(
            id = "secure",
            title = "Almost yours.",
            description = "Complete your details and we’ll handle the rest.",
            badge = "SECURE",
            tone = CheckoutHeroTone.VIOLET,
        ),
        CheckoutHeroSlide(
            id = "delivery",
            title = "Delivery, your way.",
            description = "Choose the pace that fits your week and budget.",
            badge = "FLEXIBLE",
            tone = CheckoutHeroTone.CYAN,
        ),
        CheckoutHeroSlide(
            id = "total",
            title = "Clear from the start.",
            description = "Review the full total before placing your order.",
            badge = "NO SURPRISES",
            tone = CheckoutHeroTone.LEMON,
        ),
    )

@Composable
private fun OrderSummaryCard(items: List<OrderItem>) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        shadowElevation = 1.dp,
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("Your order", style = MaterialTheme.typography.titleLarge)
                Surface(
                    shape = RoundedCornerShape(100.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                ) {
                    Text(
                        text = "${items.sumOf { it.quantity }} item",
                        modifier = Modifier.padding(horizontal = 11.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
            items.forEachIndexed { index, item ->
                if (index > 0) {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 14.dp),
                        color = MaterialTheme.colorScheme.outlineVariant,
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(item.image.drawableRes()),
                        contentDescription = null,
                        modifier =
                            Modifier
                                .size(88.dp)
                                .clip(RoundedCornerShape(18.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant),
                        contentScale = ContentScale.Crop,
                    )
                    Spacer(Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = item.name,
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Spacer(Modifier.height(3.dp))
                        Text(
                            text = item.variant,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Spacer(Modifier.height(10.dp))
                        Text(
                            text = "Qty ${item.quantity}",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.secondary,
                        )
                    }
                    Text(
                        text = formatMoney(item.lineTotalCents),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                    )
                }
            }
        }
    }
}

@Composable
private fun CheckoutSection(content: @Composable ColumnScope.() -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        shadowElevation = 1.dp,
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            content = content,
        )
    }
}

@Composable
private fun SectionHeading(
    number: String,
    title: String,
    subtitle: String,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Surface(
            modifier = Modifier.size(42.dp),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.primary,
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(number, style = MaterialTheme.typography.labelLarge)
            }
        }
        Spacer(Modifier.width(12.dp))
        Column {
            Text(title, style = MaterialTheme.typography.titleLarge)
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun CheckoutTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    error: String?,
    onFocusLost: () -> Unit,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    var hasReceivedFocus by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier =
            modifier
                .animateContentSize()
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        hasReceivedFocus = true
                    } else if (hasReceivedFocus) {
                        onFocusLost()
                    }
                },
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        supportingText = error?.let { message -> { Text(message) } },
        isError = error != null,
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        keyboardOptions = keyboardOptions,
        keyboardActions =
            KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) },
                onDone = { focusManager.clearFocus() },
            ),
        colors =
            OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                errorContainerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.22f),
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                cursorColor = MaterialTheme.colorScheme.primary,
            ),
    )
}

@Composable
private fun ShippingOptionCard(
    option: ShippingOption,
    selected: Boolean,
    onSelected: () -> Unit,
) {
    val containerColor by
        animateColorAsState(
            targetValue =
                if (selected) {
                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.52f)
                } else {
                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.62f)
                },
            label = "shipping option container",
        )
    val borderColor by
        animateColorAsState(
            targetValue = if (selected) MaterialTheme.colorScheme.primary else Color.Transparent,
            label = "shipping option border",
        )

    Surface(
        modifier =
            Modifier
                .fillMaxWidth()
                .border(1.5.dp, borderColor, RoundedCornerShape(18.dp))
                .selectable(
                    selected = selected,
                    onClick = onSelected,
                    role = Role.RadioButton,
                ).semantics(mergeDescendants = true) {
                    contentDescription = "${option.name}, ${option.arrivalEstimate}, ${formatMoney(option.priceCents)}"
                    stateDescription = if (selected) "Selected" else "Not selected"
                },
        shape = RoundedCornerShape(18.dp),
        color = containerColor,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RadioButton(
                selected = selected,
                onClick = null,
                colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary),
            )
            Spacer(Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(option.name, style = MaterialTheme.typography.titleMedium)
                    option.badge?.let { badge ->
                        Spacer(Modifier.width(8.dp))
                        Surface(
                            shape = RoundedCornerShape(100.dp),
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        ) {
                            Text(
                                text = badge,
                                modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp),
                                style = MaterialTheme.typography.labelMedium,
                            )
                        }
                    }
                }
                Text(
                    text = option.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = option.arrivalEstimate,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.secondary,
                )
            }
            Spacer(Modifier.width(8.dp))
            Text(
                text = formatMoney(option.priceCents),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.ExtraBold,
            )
        }
    }
}

@Composable
private fun PaymentMethodCard(
    payment: PaymentMethod,
    selected: Boolean,
    onSelected: () -> Unit,
) {
    val containerColor by
        animateColorAsState(
            targetValue =
                if (selected) {
                    MaterialTheme.colorScheme.primaryContainer
                } else {
                    MaterialTheme.colorScheme.surfaceVariant
                },
            label = "payment method container",
        )
    val borderColor by
        animateColorAsState(
            targetValue = if (selected) MaterialTheme.colorScheme.primary else Color.Transparent,
            label = "payment method border",
        )

    Surface(
        modifier =
            Modifier
                .fillMaxWidth()
                .border(1.5.dp, borderColor, RoundedCornerShape(18.dp))
                .selectable(
                    selected = selected,
                    onClick = onSelected,
                    role = Role.RadioButton,
                ).semantics(mergeDescendants = true) {
                    contentDescription = "${payment.name}, ${payment.description}"
                    stateDescription = if (selected) "Selected" else "Not selected"
                },
        shape = RoundedCornerShape(18.dp),
        color = containerColor,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            PaymentBadge(payment.type)
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(payment.name, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = payment.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            RadioButton(
                selected = selected,
                onClick = null,
                colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary),
            )
        }
    }
}

@Composable
private fun PaymentBadge(type: PaymentType) {
    val containerColor =
        when (type) {
            PaymentType.CARD -> MaterialTheme.colorScheme.secondary
            PaymentType.DIGITAL_WALLET -> MaterialTheme.colorScheme.primary
        }
    val contentColor =
        when (type) {
            PaymentType.CARD -> MaterialTheme.colorScheme.onSecondary
            PaymentType.DIGITAL_WALLET -> MaterialTheme.colorScheme.onPrimary
        }

    Surface(
        modifier = Modifier.size(width = 48.dp, height = 34.dp),
        shape = RoundedCornerShape(9.dp),
        color = containerColor,
        contentColor = contentColor,
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = if (type == PaymentType.CARD) "VISA" else "•••",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Black,
            )
        }
    }
}

@Composable
private fun OrderTotalCard(uiState: CheckoutUiState) {
    Surface(
        modifier = Modifier.fillMaxWidth().animateContentSize(),
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        shadowElevation = 1.dp,
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text("Order total", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(16.dp))
            PriceRow(label = "Subtotal", value = formatMoney(uiState.subtotalCents))
            Spacer(Modifier.height(9.dp))
            PriceRow(label = "Shipping", value = formatMoney(uiState.shippingCents))
            Spacer(Modifier.height(9.dp))
            PriceRow(label = "Estimated tax", value = formatMoney(uiState.taxCents))
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                color = MaterialTheme.colorScheme.outlineVariant,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
            ) {
                Column {
                    Text("Total", style = MaterialTheme.typography.titleLarge)
                    Text(
                        text = "CAD · taxes included",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                Text(
                    text = formatMoney(uiState.finalTotalCents),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.ExtraBold,
                )
            }
        }
    }
}

@Composable
private fun PriceRow(
    label: String,
    value: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(label, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun OrderSuccessDialog(
    orderNumber: String,
    email: String,
    arrivalEstimate: String,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Surface(
                modifier =
                    Modifier
                        .size(64.dp)
                        .semantics { contentDescription = "Order confirmed" },
                shape = CircleShape,
                color = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text("✓", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Black)
                }
            }
        },
        title = {
            Text(
                text = "Order confirmed!",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "Order $orderNumber has been confirmed.",
                    style = MaterialTheme.typography.bodyLarge,
                )
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Text("Estimated delivery · $arrivalEstimate", style = MaterialTheme.typography.labelLarge)
                        Text(
                            text = "Receipt email · $email",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = onDismiss, shape = RoundedCornerShape(14.dp)) {
                Text("Done")
            }
        },
        shape = RoundedCornerShape(28.dp),
        containerColor = MaterialTheme.colorScheme.surface,
    )
}

private fun ProductImage.drawableRes(): Int =
    when (this) {
        ProductImage.HEADPHONES -> R.drawable.product_headphones
    }

private val CheckoutField.validationLabel: String
    get() =
        when (this) {
            CheckoutField.FULL_NAME -> "full name"
            CheckoutField.EMAIL -> "email address"
            CheckoutField.ADDRESS -> "street address"
            CheckoutField.CITY -> "city"
            CheckoutField.POSTAL_CODE -> "postal code"
        }

private fun formatMoney(cents: Int): String {
    if (cents == 0) return "Free"
    val dollars = cents / 100
    val remainder = cents % 100
    return "\$$dollars.${remainder.toString().padStart(2, '0')}"
}

@Preview(name = "Checkout", showBackground = true, heightDp = 920)
@Composable
private fun CheckoutScreenPreview() {
    CheckoutTheme(darkTheme = false) {
        CheckoutScreen(
            uiState =
                CheckoutUiState(
                    content = MockCheckoutData.content,
                    fullName = "Maya Chen",
                    email = "maya@example.com",
                    address = "88 Harbour Street",
                    city = "Toronto",
                    postalCode = "M5J 2N8",
                    selectedShippingId = MockCheckoutData.STANDARD_SHIPPING_ID,
                    selectedPaymentId = MockCheckoutData.CARD_PAYMENT_ID,
                ),
            onFullNameChanged = {},
            onEmailChanged = {},
            onAddressChanged = {},
            onCityChanged = {},
            onPostalCodeChanged = {},
            onFieldBlurred = {},
            onShippingSelected = {},
            onPaymentSelected = {},
            onPlaceOrder = {},
            onConfirmationDismissed = {},
        )
    }
}

@Preview(name = "Validation state", showBackground = true, heightDp = 920)
@Composable
private fun CheckoutValidationPreview() {
    CheckoutTheme(darkTheme = false) {
        CheckoutScreen(
            uiState =
                CheckoutUiState(
                    content = MockCheckoutData.content,
                    fullName = "Maya",
                    email = "maya@",
                    selectedShippingId = MockCheckoutData.EXPRESS_SHIPPING_ID,
                    selectedPaymentId = MockCheckoutData.WALLET_PAYMENT_ID,
                    touchedFields = CheckoutField.entries.toSet(),
                    errors =
                        mapOf(
                            CheckoutField.FULL_NAME to "Enter a name with at least one letter",
                            CheckoutField.EMAIL to "Enter a valid email address",
                            CheckoutField.ADDRESS to "Enter your street address",
                            CheckoutField.CITY to "Enter your city",
                            CheckoutField.POSTAL_CODE to "Enter your postal code",
                        ),
                ),
            onFullNameChanged = {},
            onEmailChanged = {},
            onAddressChanged = {},
            onCityChanged = {},
            onPostalCodeChanged = {},
            onFieldBlurred = {},
            onShippingSelected = {},
            onPaymentSelected = {},
            onPlaceOrder = {},
            onConfirmationDismissed = {},
        )
    }
}
