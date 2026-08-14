package com.example.productgrid.ui.productgrid

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.productgrid.R
import com.example.productgrid.data.mock.MockProducts
import com.example.productgrid.data.model.Product
import com.example.productgrid.data.model.ProductImage
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
    containerColor = MaterialTheme.colorScheme.background,
    topBar = {
      CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
        title = {
          Text(
            text = "COLOR MARKET",
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.8.sp,
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
    border = BorderStroke(1.dp, palette.accent.copy(alpha = 0.38f)),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
  ) {
    Box(
      modifier =
        Modifier.fillMaxWidth()
          .aspectRatio(1.1f)
          .background(palette.well),
    ) {
      Image(
        painter = painterResource(product.image.drawableRes()),
        contentDescription = "${product.name} product image",
        modifier =
          Modifier.fillMaxSize()
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
            .drawWithContent {
              drawContent()
              drawRect(
                color = palette.well.copy(alpha = 0.82f),
                blendMode = BlendMode.Multiply,
              )
            },
        contentScale = ContentScale.Crop,
      )

      if (product.discountPercent > 0) {
        Surface(
          modifier = Modifier.align(Alignment.TopStart).padding(10.dp),
          shape = RoundedCornerShape(8.dp),
          color = MaterialTheme.colorScheme.inverseSurface,
          contentColor = MaterialTheme.colorScheme.inverseOnSurface,
        ) {
          Text(
            text = "-${product.discountPercent}%",
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
          )
        }
      }

      val favoriteAction = if (product.isFavorite) "Remove from favorites" else "Add to favorites"
      Surface(
        modifier = Modifier.align(Alignment.TopEnd).padding(8.dp),
        shape = RoundedCornerShape(14.dp),
        color = palette.accent,
        contentColor = palette.onAccent,
        shadowElevation = 3.dp,
      ) {
        IconButton(
          onClick = onFavoriteClick,
          modifier = Modifier.semantics { contentDescription = "$favoriteAction: ${product.name}" },
        ) {
          Text(
            text = if (product.isFavorite) "♥︎" else "♡",
            color = palette.onAccent,
            style = MaterialTheme.typography.headlineSmall,
          )
        }
      }
    }

    Column(
      modifier = Modifier.padding(12.dp),
      verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
      Surface(
        shape = RoundedCornerShape(100.dp),
        color = palette.accent,
        contentColor = palette.onAccent,
      ) {
        Text(
          text = product.category.uppercase(Locale.US),
          modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
          style = MaterialTheme.typography.labelSmall,
          fontWeight = FontWeight.Black,
          letterSpacing = 0.6.sp,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis,
        )
      }
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
        color = palette.accent,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Black,
      )
    }
  }
}

private data class ProductPalette(
  val well: Color,
  val accent: Color,
  val onAccent: Color,
)

private val lightProductPalettes =
  listOf(
    ProductPalette(well = Color(0xFFFF5A4F), accent = Color(0xFFA61B14), onAccent = Color.White),
    ProductPalette(well = Color(0xFF00A86B), accent = Color(0xFF00613E), onAccent = Color.White),
    ProductPalette(well = Color(0xFFFFD400), accent = Color(0xFF6B5200), onAccent = Color.White),
    ProductPalette(well = Color(0xFF2F5BFF), accent = Color(0xFF173BB9), onAccent = Color.White),
    ProductPalette(well = Color(0xFF7C3AED), accent = Color(0xFF5B21B6), onAccent = Color.White),
    ProductPalette(well = Color(0xFFE91E63), accent = Color(0xFFA30D43), onAccent = Color.White),
    ProductPalette(well = Color(0xFF00B8D9), accent = Color(0xFF006778), onAccent = Color.White),
    ProductPalette(well = Color(0xFFFF7A00), accent = Color(0xFF9A4300), onAccent = Color.White),
  )

private val darkProductPalettes =
  listOf(
    ProductPalette(well = Color(0xFFFF5A4F), accent = Color(0xFFFF8B83), onAccent = Color(0xFF23100E)),
    ProductPalette(well = Color(0xFF00A86B), accent = Color(0xFF4FE3A5), onAccent = Color(0xFF002117)),
    ProductPalette(well = Color(0xFFFFD400), accent = Color(0xFFFFE066), onAccent = Color(0xFF241A00)),
    ProductPalette(well = Color(0xFF2F5BFF), accent = Color(0xFF9DB0FF), onAccent = Color(0xFF071D58)),
    ProductPalette(well = Color(0xFF7C3AED), accent = Color(0xFFC8A6FF), onAccent = Color(0xFF28104C)),
    ProductPalette(well = Color(0xFFE91E63), accent = Color(0xFFFF80B0), onAccent = Color(0xFF3C0820)),
    ProductPalette(well = Color(0xFF00B8D9), accent = Color(0xFF6CEAFF), onAccent = Color(0xFF002027)),
    ProductPalette(well = Color(0xFFFF7A00), accent = Color(0xFFFFB168), onAccent = Color(0xFF301400)),
  )

@Composable
private fun productPalette(productId: Long): ProductPalette {
  val palettes = if (isSystemInDarkTheme()) darkProductPalettes else lightProductPalettes
  val index = ((productId - 1).mod(palettes.size.toLong())).toInt()
  return palettes[index]
}

private fun ProductImage.drawableRes(): Int =
  when (this) {
    ProductImage.HEADPHONES -> R.drawable.product_headphones
    ProductImage.KEYBOARD -> R.drawable.product_keyboard
    ProductImage.SMARTWATCH -> R.drawable.product_smartwatch
    ProductImage.SPEAKER -> R.drawable.product_speaker
    ProductImage.CAMERA -> R.drawable.product_camera
    ProductImage.MOUSE -> R.drawable.product_mouse
    ProductImage.E_READER -> R.drawable.product_e_reader
    ProductImage.PROJECTOR -> R.drawable.product_projector
    ProductImage.CHARGING_HUB -> R.drawable.product_charging_hub
    ProductImage.MICROPHONE -> R.drawable.product_microphone
    ProductImage.BACKPACK -> R.drawable.product_backpack
    ProductImage.DESK_LAMP -> R.drawable.product_desk_lamp
    ProductImage.TRAVEL_BOTTLE -> R.drawable.product_travel_bottle
    ProductImage.PHOTO_PRINTER -> R.drawable.product_photo_printer
    ProductImage.TABLET_STAND -> R.drawable.product_tablet_stand
    ProductImage.AIR_PURIFIER -> R.drawable.product_air_purifier
    ProductImage.HANDHELD_CONSOLE -> R.drawable.product_handheld_console
    ProductImage.MINI_VACUUM -> R.drawable.product_mini_vacuum
    ProductImage.YOGA_MAT -> R.drawable.product_yoga_mat
    ProductImage.COFFEE_GRINDER -> R.drawable.product_coffee_grinder
    ProductImage.CHARGING_PAD -> R.drawable.product_charging_pad
    ProductImage.KITCHEN_SCALE -> R.drawable.product_kitchen_scale
    ProductImage.ALARM_CLOCK -> R.drawable.product_alarm_clock
    ProductImage.TRAVEL_UMBRELLA -> R.drawable.product_travel_umbrella
    ProductImage.BINOCULARS -> R.drawable.product_binoculars
    ProductImage.COOKWARE -> R.drawable.product_cookware
    ProductImage.PLANT_POT -> R.drawable.product_plant_pot
    ProductImage.RUNNING_SHOES -> R.drawable.product_running_shoes
    ProductImage.SUNGLASSES -> R.drawable.product_sunglasses
    ProductImage.TRAVEL_CASE -> R.drawable.product_travel_case
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
  ProductGridTheme(darkTheme = false) {
    ProductGridScreen(
      products = MockProducts.items,
      onFavoriteClick = {},
    )
  }
}

@Preview(
  name = "Product grid · Dark",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun ProductGridScreenDarkPreview() {
  ProductGridTheme(darkTheme = true) {
    ProductGridScreen(
      products = MockProducts.items,
      onFavoriteClick = {},
    )
  }
}
