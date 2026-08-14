package com.example.checkout.ui.checkout

import androidx.lifecycle.ViewModel
import com.example.checkout.data.model.CheckoutContent
import com.example.checkout.data.model.CheckoutRequest
import com.example.checkout.data.model.OrderConfirmation
import com.example.checkout.data.model.PaymentMethod
import com.example.checkout.data.model.ShippingOption
import com.example.checkout.data.repository.CheckoutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class CheckoutField {
    FULL_NAME,
    EMAIL,
    ADDRESS,
    CITY,
    POSTAL_CODE,
}

data class CheckoutUiState(
    val content: CheckoutContent,
    val fullName: String = "",
    val email: String = "",
    val address: String = "",
    val city: String = "",
    val postalCode: String = "",
    val selectedShippingId: String,
    val selectedPaymentId: String,
    val touchedFields: Set<CheckoutField> = emptySet(),
    val errors: Map<CheckoutField, String> = emptyMap(),
    val showValidationSummary: Boolean = false,
    val orderConfirmation: OrderConfirmation? = null,
    val isConfirmationDialogVisible: Boolean = false,
) {
    val selectedShipping: ShippingOption
        get() = content.shippingOptions.first { it.id == selectedShippingId }

    val selectedPayment: PaymentMethod
        get() = content.paymentMethods.first { it.id == selectedPaymentId }

    val subtotalCents: Int
        get() = content.subtotalCents

    val shippingCents: Int
        get() = selectedShipping.priceCents

    val taxCents: Int
        get() = (subtotalCents * content.taxRateBasisPoints + 5_000) / 10_000

    val finalTotalCents: Int
        get() = subtotalCents + shippingCents + taxCents

    fun visibleError(field: CheckoutField): String? =
        if (field in touchedFields) errors[field] else null
}

class CheckoutViewModel(
    private val checkoutRepository: CheckoutRepository,
) : ViewModel() {
    private val checkoutContent = checkoutRepository.getCheckoutContent()

    private val _uiState =
        MutableStateFlow(
            CheckoutUiState(
                content = checkoutContent,
                selectedShippingId = checkoutContent.shippingOptions.first().id,
                selectedPaymentId = checkoutContent.paymentMethods.first().id,
            ),
        )
    val uiState: StateFlow<CheckoutUiState> = _uiState.asStateFlow()

    fun onFullNameChanged(value: String) = updateField(CheckoutField.FULL_NAME, value)

    fun onEmailChanged(value: String) = updateField(CheckoutField.EMAIL, value)

    fun onAddressChanged(value: String) = updateField(CheckoutField.ADDRESS, value)

    fun onCityChanged(value: String) = updateField(CheckoutField.CITY, value)

    fun onPostalCodeChanged(value: String) =
        updateField(
            field = CheckoutField.POSTAL_CODE,
            value = value.uppercase().take(MAX_POSTAL_LENGTH),
        )

    fun onFieldBlurred(field: CheckoutField) {
        _uiState.update { state ->
            state.copy(
                touchedFields = state.touchedFields + field,
                errors = state.errors.withValidation(field, CheckoutValidator.validate(field, state.valueOf(field))),
            )
        }
    }

    fun onShippingSelected(shippingId: String) {
        if (checkoutContent.shippingOptions.none { it.id == shippingId }) return
        _uiState.update { it.copy(selectedShippingId = shippingId) }
    }

    fun onPaymentSelected(paymentId: String) {
        if (checkoutContent.paymentMethods.none { it.id == paymentId }) return
        _uiState.update { it.copy(selectedPaymentId = paymentId) }
    }

    fun onPlaceOrder() {
        val state = _uiState.value
        if (state.orderConfirmation != null) return

        val validationErrors = CheckoutField.entries.mapNotNull { field ->
            CheckoutValidator.validate(field, state.valueOf(field))?.let { field to it }
        }.toMap()

        if (validationErrors.isNotEmpty()) {
            _uiState.update {
                it.copy(
                    touchedFields = CheckoutField.entries.toSet(),
                    errors = validationErrors,
                    showValidationSummary = true,
                )
            }
            return
        }

        val confirmation =
            checkoutRepository.submitOrder(
                CheckoutRequest(
                    fullName = state.fullName.trim(),
                    email = state.email.trim(),
                    address = state.address.trim(),
                    city = state.city.trim(),
                    postalCode = state.postalCode.trim(),
                    shippingOptionId = state.selectedShippingId,
                    paymentMethodId = state.selectedPaymentId,
                    totalCents = state.finalTotalCents,
                ),
            )
        _uiState.update {
            it.copy(
                errors = emptyMap(),
                showValidationSummary = false,
                orderConfirmation = confirmation,
                isConfirmationDialogVisible = true,
            )
        }
    }

    fun onConfirmationDismissed() {
        _uiState.update { it.copy(isConfirmationDialogVisible = false) }
    }

    private fun updateField(
        field: CheckoutField,
        value: String,
    ) {
        _uiState.update { state ->
            val updatedState = state.withValue(field, value)
            if (field !in updatedState.touchedFields) {
                updatedState
            } else {
                updatedState.copy(
                    errors = updatedState.errors.withValidation(field, CheckoutValidator.validate(field, value)),
                )
            }
        }
    }

    private companion object {
        const val MAX_POSTAL_LENGTH = 7
    }
}

private fun CheckoutUiState.valueOf(field: CheckoutField): String =
    when (field) {
        CheckoutField.FULL_NAME -> fullName
        CheckoutField.EMAIL -> email
        CheckoutField.ADDRESS -> address
        CheckoutField.CITY -> city
        CheckoutField.POSTAL_CODE -> postalCode
    }

private fun CheckoutUiState.withValue(
    field: CheckoutField,
    value: String,
): CheckoutUiState =
    when (field) {
        CheckoutField.FULL_NAME -> copy(fullName = value)
        CheckoutField.EMAIL -> copy(email = value)
        CheckoutField.ADDRESS -> copy(address = value)
        CheckoutField.CITY -> copy(city = value)
        CheckoutField.POSTAL_CODE -> copy(postalCode = value)
    }

private fun Map<CheckoutField, String>.withValidation(
    field: CheckoutField,
    message: String?,
): Map<CheckoutField, String> =
    toMutableMap().apply {
        if (message == null) remove(field) else this[field] = message
    }

private object CheckoutValidator {
    private val emailPattern = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    private val postalPattern = Regex("^[A-Za-z]\\d[A-Za-z][ -]?\\d[A-Za-z]\\d$")

    fun validate(
        field: CheckoutField,
        rawValue: String,
    ): String? {
        val value = rawValue.trim()
        return when (field) {
            CheckoutField.FULL_NAME ->
                when {
                    value.isEmpty() -> "Enter your full name"
                    value.none { character -> character.isLetter() } -> "Enter a name with at least one letter"
                    else -> null
                }

            CheckoutField.EMAIL ->
                when {
                    value.isEmpty() -> "Enter your email address"
                    !emailPattern.matches(value) -> "Enter a valid email address"
                    else -> null
                }

            CheckoutField.ADDRESS ->
                when {
                    value.isEmpty() -> "Enter your street address"
                    value.length < 6 -> "Enter a complete street address"
                    else -> null
                }

            CheckoutField.CITY ->
                when {
                    value.isEmpty() -> "Enter your city"
                    value.length < 2 -> "Enter a valid city"
                    else -> null
                }

            CheckoutField.POSTAL_CODE ->
                when {
                    value.isEmpty() -> "Enter your postal code"
                    !postalPattern.matches(value) -> "Use a Canadian postal code (A1A 1A1)"
                    else -> null
                }
        }
    }
}
