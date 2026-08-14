package com.example.productdetail.ui.productdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
    topBar = { CenterAlignedTopAppBar(title = { Text("Product Details", fontWeight = FontWeight.Bold) }) },
    bottomBar = {
      Surface(shadowElevation = 8.dp) {
        Row(
          modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 14.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text("Total", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(
              String.format(Locale.US, "$%.2f", product.price * detail.quantity),
              style = MaterialTheme.typography.titleLarge,
              fontWeight = FontWeight.Bold,
            )
          }
          Button(onClick = onAddToCartClick, contentPadding = PaddingValues(horizontal = 24.dp, vertical = 14.dp)) {
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
        Box {
          Image(
            painter = painterResource(R.drawable.product_sneaker),
            contentDescription = product.name,
            modifier = Modifier.fillMaxWidth().height(280.dp).clip(RoundedCornerShape(28.dp)),
            contentScale = ContentScale.Crop,
          )
          IconButton(onClick = onFavoriteClick, modifier = Modifier.align(Alignment.TopEnd).padding(12.dp)) {
            Text(if (detail.isFavorite) "♥︎" else "♡", style = MaterialTheme.typography.headlineMedium, color = Color(0xFFE4496F))
          }
        }
      }
      item {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Text(product.category.uppercase(Locale.US), color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
          Text(product.name, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
          Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("★", color = Color(0xFFFFA000))
            Text("${product.rating} (${product.reviewCount} reviews)", color = MaterialTheme.colorScheme.onSurfaceVariant)
          }
          Text(String.format(Locale.US, "$%.2f", product.price), style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        }
      }
      item {
        SelectionSection("Color") {
          Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            product.colors.forEach { color ->
              FilterChip(selected = detail.selectedColor == color, onClick = { onColorSelected(color) }, label = { Text(color.label) })
            }
          }
        }
      }
      item {
        SelectionSection("Size") {
          Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            product.sizes.forEach { size ->
              if (detail.selectedSize == size) {
                Button(onClick = { onSizeSelected(size) }, shape = CircleShape, contentPadding = PaddingValues(0.dp), modifier = Modifier.size(48.dp)) { Text(size.toString()) }
              } else {
                OutlinedButton(onClick = { onSizeSelected(size) }, shape = CircleShape, contentPadding = PaddingValues(0.dp), modifier = Modifier.size(48.dp)) { Text(size.toString()) }
              }
            }
          }
        }
      }
      item {
        SelectionSection("Quantity") {
          Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              IconButton(onClick = onDecreaseQuantity) { Text("−", style = MaterialTheme.typography.titleLarge) }
              Text(detail.quantity.toString(), modifier = Modifier.width(36.dp), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
              IconButton(onClick = onIncreaseQuantity) { Text("+", style = MaterialTheme.typography.titleLarge) }
            }
          }
        }
      }
      item {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Text("About this product", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
          Text(product.description, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyLarge)
        }
      }
    }
  }
}

@Composable
private fun SelectionSection(title: String, content: @Composable () -> Unit) {
  Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
    Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
    content()
  }
}

@Preview(showBackground = true)
@Composable
private fun ProductDetailScreenPreview() {
  ProductDetailTheme {
    ProductDetailScreen(MockProduct.detail, {}, {}, {}, {}, {}, {})
  }
}
