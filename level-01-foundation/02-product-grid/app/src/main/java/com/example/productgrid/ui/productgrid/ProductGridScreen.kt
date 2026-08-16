package com.example.productgrid.ui.productgrid

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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
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

private val ProductImageBackground = Color(0xFFFDFAF8)

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
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
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
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.05f)
                .background(ProductImageBackground)
                .clipToBounds(),
        ) {
            Image(
                painter = painterResource(product.image.drawableRes()),
                contentDescription = "${product.name} product image",
                modifier = Modifier
                    .matchParentSize()
                    .graphicsLayer {
                        scaleX = 1.025f
                        scaleY = 1.025f
                    },
                contentScale = ContentScale.Crop,
            )
        }

        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            val favoriteAction =
                if (product.isFavorite) "Remove from favorites" else "Add to favorites"
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Surface(
                    shape = RoundedCornerShape(100.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
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

                IconButton(
                    onClick = onFavoriteClick,
                    modifier = Modifier
                        .size(40.dp)
                        .semantics { contentDescription = "$favoriteAction: ${product.name}" },
                ) {
                    Icon(
                        imageVector = if (product.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = null,
                        tint = if (product.isFavorite) {
                            MaterialTheme.colorScheme.error
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        },
                    )
                }
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
            Column(
                modifier = Modifier.height(58.dp),
            ) {
                Spacer(modifier = Modifier.weight(1f))
                if (product.discountPercent > 0) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError,
                    ) {
                        Text(
                            text = "-${product.discountPercent}%",
                            modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = formatPrice(product.price),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Black,
                    )
                    if (product.discountPercent > 0) {
                        Text(
                            text = formatPrice(originalPrice(product)),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodySmall,
                            textDecoration = TextDecoration.LineThrough,
                        )
                    }
                }
            }
        }
    }
}

private fun ProductImage.drawableRes(): Int = when (this) {
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

private fun formatReviewCount(reviewCount: Int): String = if (reviewCount >= 1_000) {
    String.format(Locale.US, "%.1fK", reviewCount / 1_000.0)
} else {
    reviewCount.toString()
}

private fun originalPrice(product: Product): Double =
    product.price / (1 - product.discountPercent / 100.0)

private fun formatPrice(price: Double): String = String.format(Locale.US, "$%.2f", price)

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
