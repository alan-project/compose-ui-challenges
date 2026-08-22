package com.example.composemarket.ui.composemarket

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.composemarket.data.mock.MockProducts
import com.example.composemarket.data.model.Product
import com.example.composemarket.theme.ComposeMarketTheme
import java.util.Locale

private val FavoriteColor = Color(0xFFC45A64)
private val RatingStarColor = Color(0xFFE2A51B)
private val DiscountColor = Color(0xFFC62828)

@Composable
fun ComposeMarketRoute(
    viewModel: ComposeMarketViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ComposeMarketScreen(
        products = uiState.products,
        onFavoriteClick = viewModel::onFavoriteClick,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposeMarketScreen(
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
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
                title = {
                    Text(
                        text = "COMPOSE MARKET",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.5.sp,
                    )
                },
            )
        },
    ) { innerPadding ->

        // Compose market
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
        // Product image
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.05f)
                .clipToBounds(),
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(product.imageUrl)
                    .crossfade(true).build(),
                contentDescription = "${product.name} product image",
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop,
            )
        }

        Column(
            modifier = Modifier
                .padding(12.dp)
                .height(184.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Category
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

                // Favorite
                IconButton(
                    onClick = onFavoriteClick,
                    modifier = Modifier.size(48.dp),
                ) {
                    Icon(
                        imageVector = if (product.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = if (product.isFavorite) {
                            "Remove from favorites"
                        } else {
                            "Add to favorites"
                        },
                        tint = if (product.isFavorite) {
                            FavoriteColor
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        },
                    )
                }
            }
            Spacer(modifier = Modifier.height(6.dp))

            // Product name
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(6.dp))

            // Rating
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = RatingStarColor,
                    modifier = Modifier.size(16.dp),
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${product.rating} (${formatReviewCount(product.reviewCount)})",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier.height(58.dp),
            ) {
                Spacer(modifier = Modifier.weight(1f))

                // Discount
                if (product.discountPercent > 0) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = DiscountColor,
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

                // Price
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
private fun ComposeMarketScreenPreview() {
    ComposeMarketTheme(darkTheme = false) {
        ComposeMarketScreen(
            products = MockProducts.items,
            onFavoriteClick = {},
        )
    }
}

@Preview(
    name = "Compose market · Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun ComposeMarketScreenDarkPreview() {
    ComposeMarketTheme(darkTheme = true) {
        ComposeMarketScreen(
            products = MockProducts.items,
            onFavoriteClick = {},
        )
    }
}
