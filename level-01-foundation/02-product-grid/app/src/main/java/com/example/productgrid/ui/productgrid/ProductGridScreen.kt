package com.example.productgrid.ui.productgrid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.productgrid.data.mock.MockProducts
import com.example.productgrid.data.model.Product
import com.example.productgrid.theme.ProductGridTheme
import java.util.Locale

@Composable
fun ProductGridRoute(
  viewModel: ProductGridViewModel,
  modifier: Modifier = Modifier,
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  ProductGridScreen(
    products = uiState.products,
    onFavoriteClick = viewModel::onFavoriteClick,
    modifier = modifier,
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductGridScreen(
  products: List<Product>,
  onFavoriteClick: (Long) -> Unit,
  modifier: Modifier = Modifier,
) {
  Scaffold(
    modifier = modifier.fillMaxSize(),
    contentWindowInsets = WindowInsets.safeDrawing,
    topBar = {
      CenterAlignedTopAppBar(
        title = {
          Text(
            text = "Discover",
            fontWeight = FontWeight.Bold,
          )
        },
      )
    },
  ) { innerPadding ->
    LazyVerticalGrid(
      columns = GridCells.Fixed(2),
      modifier = Modifier.fillMaxSize().padding(innerPadding),
      contentPadding = PaddingValues(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp),
      horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
      items(
        items = products,
        key = Product::id,
      ) { product ->
        ProductCard(
          product = product,
          onFavoriteClick = { onFavoriteClick(product.id) },
        )
      }
    }
  }
}

@Composable
private fun ProductCard(
  product: Product,
  onFavoriteClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val palette = productPalette(product.id)

  Card(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
  ) {
    Box(
      modifier =
        Modifier.fillMaxWidth()
          .aspectRatio(1.1f)
          .background(palette.background),
    ) {
      Text(
        text = product.symbol,
        modifier =
          Modifier.align(Alignment.Center).semantics {
            contentDescription = "${product.name} product image"
          },
        fontSize = 52.sp,
      )

      if (product.discountPercent > 0) {
        Surface(
          modifier = Modifier.align(Alignment.TopStart).padding(10.dp),
          shape = RoundedCornerShape(8.dp),
          color = MaterialTheme.colorScheme.errorContainer,
        ) {
          Text(
            text = "-${product.discountPercent}%",
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            color = MaterialTheme.colorScheme.onErrorContainer,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
          )
        }
      }

      val favoriteAction = if (product.isFavorite) "Remove from favorites" else "Add to favorites"
      IconButton(
        onClick = onFavoriteClick,
        modifier =
          Modifier.align(Alignment.TopEnd)
            .padding(6.dp)
            .semantics { contentDescription = "$favoriteAction: ${product.name}" },
      ) {
        Text(
          text = if (product.isFavorite) "♥︎" else "♡",
          color =
            if (product.isFavorite) {
              palette.accent
            } else {
              palette.accent.copy(alpha = 0.78f)
            },
          style = MaterialTheme.typography.headlineSmall,
        )
      }
    }

    Column(
      modifier = Modifier.padding(12.dp),
      verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
      Text(
        text = product.category.uppercase(Locale.US),
        color = palette.accent,
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.Bold,
      )
      Text(
        text = product.name,
        modifier = Modifier.heightIn(min = 48.dp),
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
      )
      Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
          text = "★",
          color = Color(0xFFFFA000),
          style = MaterialTheme.typography.bodySmall,
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
          text = "${product.rating} (${formatReviewCount(product.reviewCount)})",
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          style = MaterialTheme.typography.bodySmall,
        )
      }
      Text(
        text = String.format(Locale.US, "$%.2f", product.price),
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
      )
    }
  }
}

private data class ProductPalette(
  val background: Color,
  val accent: Color,
)

private val productPalettes =
  listOf(
    ProductPalette(background = Color(0xFFFFD4C8), accent = Color(0xFFA93428)),
    ProductPalette(background = Color(0xFFC9F1DF), accent = Color(0xFF08705A)),
    ProductPalette(background = Color(0xFFFFE69B), accent = Color(0xFF855A00)),
    ProductPalette(background = Color(0xFFC6DDFF), accent = Color(0xFF245DA6)),
    ProductPalette(background = Color(0xFFE0CEFF), accent = Color(0xFF6940A5)),
    ProductPalette(background = Color(0xFFFFCCDE), accent = Color(0xFFA92D5D)),
    ProductPalette(background = Color(0xFFBFECEF), accent = Color(0xFF006B73)),
    ProductPalette(background = Color(0xFFFFD2A8), accent = Color(0xFFA34D00)),
  )

private fun productPalette(productId: Long): ProductPalette {
  val index = ((productId - 1).mod(productPalettes.size.toLong())).toInt()
  return productPalettes[index]
}

private fun formatReviewCount(reviewCount: Int): String =
  if (reviewCount >= 1_000) {
    String.format(Locale.US, "%.1fK", reviewCount / 1_000.0)
  } else {
    reviewCount.toString()
  }

@Preview(showBackground = true)
@Composable
private fun ProductGridScreenPreview() {
  ProductGridTheme {
    ProductGridScreen(
      products = MockProducts.items,
      onFavoriteClick = {},
    )
  }
}
