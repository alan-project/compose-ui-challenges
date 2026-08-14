package com.example.productdetail.ui.productdetail

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.productdetail.R
import com.example.productdetail.data.mock.MockProduct
import com.example.productdetail.data.model.ProductColor
import com.example.productdetail.data.model.ProductDetail
import com.example.productdetail.theme.ProductDetailTheme
import java.util.Locale

@Composable
fun ProductDetailRoute(
  viewModel: ProductDetailViewModel,
  modifier: Modifier = Modifier,
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  ProductDetailScreen(
    detail = uiState.detail,
    onColorSelected = viewModel::onColorSelected,
    onSizeSelected = viewModel::onSizeSelected,
    onIncreaseQuantity = viewModel::onIncreaseQuantity,
    onDecreaseQuantity = viewModel::onDecreaseQuantity,
    onFavoriteClick = viewModel::onFavoriteClick,
    onAddToCartClick = viewModel::onAddToCartClick,
    modifier = modifier,
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
  detail: ProductDetail,
  onColorSelected: (ProductColor) -> Unit,
  onSizeSelected: (Int) -> Unit,
  onIncreaseQuantity: () -> Unit,
  onDecreaseQuantity: () -> Unit,
  onFavoriteClick: () -> Unit,
  onAddToCartClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val product = detail.product
  Scaffold(
    modifier = modifier.fillMaxSize(),
    contentWindowInsets = WindowInsets.safeDrawing,
    containerColor = MaterialTheme.colorScheme.background,
    topBar = {
      CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
        title = {
          Text(
            text = "AERORUN / 01",
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.4.sp,
          )
        },
      )
    },
    bottomBar = {
      Surface(
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        shadowElevation = 10.dp,
      ) {
        Row(
          modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 14.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = "TOTAL",
              style = MaterialTheme.typography.labelMedium,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              fontWeight = FontWeight.Bold,
              letterSpacing = 1.sp,
            )
            Text(
              String.format(Locale.US, "$%.2f", product.price * detail.quantity),
              color = MaterialTheme.colorScheme.secondary,
              style = MaterialTheme.typography.titleLarge,
              fontWeight = FontWeight.Black,
            )
          }
          Button(
            onClick = onAddToCartClick,
            shape = RoundedCornerShape(16.dp),
            colors =
              ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
              ),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
          ) {
            Text(if (detail.cartItemCount == 0) "Add to cart" else "Cart (${detail.cartItemCount})")
          }
        }
      }
    },
  ) { innerPadding ->
    LazyColumn(
      modifier = Modifier.fillMaxSize().padding(innerPadding),
      contentPadding = PaddingValues(20.dp),
      verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
      item {
        val imageShape = RoundedCornerShape(28.dp)
        Box(
          modifier =
            Modifier.fillMaxWidth()
              .height(280.dp)
              .clip(imageShape)
              .background(MaterialTheme.colorScheme.surface)
              .border(1.dp, MaterialTheme.colorScheme.outlineVariant, imageShape),
        ) {
          Image(
            painter = painterResource(R.drawable.product_sneaker),
            contentDescription = product.name,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
          )

          Surface(
            modifier = Modifier.align(Alignment.TopStart).padding(14.dp),
            shape = RoundedCornerShape(10.dp),
            color = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary,
          ) {
            Text(
              text = "CITY RUN / NEW",
              modifier = Modifier.padding(horizontal = 11.dp, vertical = 7.dp),
              style = MaterialTheme.typography.labelMedium,
              fontWeight = FontWeight.Black,
              letterSpacing = 0.8.sp,
            )
          }

          Surface(
            modifier =
              Modifier.align(Alignment.TopEnd)
                .padding(12.dp)
                .size(52.dp)
                .semantics {
                  contentDescription =
                    if (detail.isFavorite) "Remove ${product.name} from favorites"
                    else "Add ${product.name} to favorites"
                },
            onClick = onFavoriteClick,
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            shadowElevation = 5.dp,
          ) {
            Box(contentAlignment = Alignment.Center) {
              Text(
                text = if (detail.isFavorite) "♥︎" else "♡",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Black,
              )
            }
          }
        }
      }
      item {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Text(
            text = product.category.uppercase(Locale.US),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.2.sp,
          )
          Text(product.name, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Black)
          Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("★", color = MaterialTheme.colorScheme.tertiary)
            Text("${product.rating} (${product.reviewCount} reviews)", color = MaterialTheme.colorScheme.onSurfaceVariant)
          }
          Text(
            text = String.format(Locale.US, "$%.2f", product.price),
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Black,
          )
        }
      }
      item {
        SelectionSection("Color") {
          Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            product.colors.forEach { color ->
              val selected = detail.selectedColor == color
              FilterChip(
                selected = selected,
                onClick = { onColorSelected(color) },
                colors =
                  FilterChipDefaults.filterChipColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    labelColor = MaterialTheme.colorScheme.onSurface,
                    selectedContainerColor = MaterialTheme.colorScheme.secondary,
                    selectedLabelColor = MaterialTheme.colorScheme.onSecondary,
                  ),
                label = {
                  Row(
                    horizontalArrangement = Arrangement.spacedBy(7.dp),
                    verticalAlignment = Alignment.CenterVertically,
                  ) {
                    Surface(
                      modifier = Modifier.size(12.dp),
                      shape = CircleShape,
                      color = color.swatchColor(),
                      border =
                        BorderStroke(
                          1.dp,
                          if (selected) {
                            MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.72f)
                          } else {
                            MaterialTheme.colorScheme.outline
                          },
                        ),
                    ) {}
                    Text(color.label)
                  }
                },
              )
            }
          }
        }
      }
      item {
        SelectionSection("Size") {
          Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            product.sizes.forEach { size ->
              if (detail.selectedSize == size) {
                Button(
                  onClick = { onSizeSelected(size) },
                  shape = CircleShape,
                  colors =
                    ButtonDefaults.buttonColors(
                      containerColor = MaterialTheme.colorScheme.secondary,
                      contentColor = MaterialTheme.colorScheme.onSecondary,
                    ),
                  contentPadding = PaddingValues(0.dp),
                  modifier = Modifier.size(48.dp),
                ) {
                  Text(size.toString())
                }
              } else {
                OutlinedButton(
                  onClick = { onSizeSelected(size) },
                  shape = CircleShape,
                  colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.secondary),
                  border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                  contentPadding = PaddingValues(0.dp),
                  modifier = Modifier.size(48.dp),
                ) {
                  Text(size.toString())
                }
              }
            }
          }
        }
      }
      item {
        SelectionSection("Quantity") {
          Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              IconButton(onClick = onDecreaseQuantity) {
                Text("−", color = MaterialTheme.colorScheme.secondary, style = MaterialTheme.typography.titleLarge)
              }
              Text(
                text = detail.quantity.toString(),
                modifier = Modifier.width(36.dp),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center,
              )
              IconButton(onClick = onIncreaseQuantity) {
                Text("+", color = MaterialTheme.colorScheme.secondary, style = MaterialTheme.typography.titleLarge)
              }
            }
          }
        }
      }
      item {
        Surface(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(24.dp),
          color = MaterialTheme.colorScheme.inverseSurface,
          contentColor = MaterialTheme.colorScheme.inverseOnSurface,
        ) {
          Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(9.dp),
          ) {
            Text(
              text = "ABOUT THIS PRODUCT",
              style = MaterialTheme.typography.labelLarge,
              fontWeight = FontWeight.Black,
              letterSpacing = 1.1.sp,
            )
            Text(
              text = product.description,
              color = MaterialTheme.colorScheme.inverseOnSurface.copy(alpha = 0.84f),
              style = MaterialTheme.typography.bodyLarge,
            )
          }
        }
      }
    }
  }
}

@Composable
private fun SelectionSection(title: String, content: @Composable () -> Unit) {
  Surface(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(20.dp),
    color = MaterialTheme.colorScheme.surface,
    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
  ) {
    Column(
      modifier = Modifier.padding(18.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
      Text(
        text = title.uppercase(Locale.US),
        color = MaterialTheme.colorScheme.secondary,
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.Black,
        letterSpacing = 1.sp,
      )
      content()
    }
  }
}

@Composable
private fun ProductColor.swatchColor(): Color =
  when (this) {
    ProductColor.MIDNIGHT -> MaterialTheme.colorScheme.secondary
    ProductColor.CORAL -> MaterialTheme.colorScheme.primary
    ProductColor.CLOUD -> MaterialTheme.colorScheme.surface
  }

@Preview(showBackground = true)
@Composable
private fun ProductDetailScreenPreview() {
  ProductDetailTheme(darkTheme = false) {
    ProductDetailScreen(MockProduct.detail, {}, {}, {}, {}, {}, {})
  }
}

@Preview(
  name = "Product detail · Dark",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun ProductDetailScreenDarkPreview() {
  ProductDetailTheme(darkTheme = true) {
    ProductDetailScreen(MockProduct.detail, {}, {}, {}, {}, {}, {})
  }
}
