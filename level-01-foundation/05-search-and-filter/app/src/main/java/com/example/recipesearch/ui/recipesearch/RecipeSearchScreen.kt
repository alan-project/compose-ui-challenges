package com.example.recipesearch.ui.recipesearch

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.recipesearch.R
import com.example.recipesearch.data.mock.MockRecipes
import com.example.recipesearch.data.model.Recipe
import com.example.recipesearch.data.model.RecipeImage
import com.example.recipesearch.theme.RecipeSearchTheme

@Composable
fun RecipeSearchRoute(
  viewModel: RecipeSearchViewModel,
  modifier: Modifier = Modifier,
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  RecipeSearchScreen(
    uiState = uiState,
    onQueryChanged = viewModel::onQueryChanged,
    onCategorySelected = viewModel::onCategorySelected,
    onFavoriteClick = viewModel::onFavoriteClick,
    modifier = modifier,
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeSearchScreen(
  uiState: RecipeSearchUiState,
  onQueryChanged: (String) -> Unit,
  onCategorySelected: (String) -> Unit,
  onFavoriteClick: (Long) -> Unit,
  modifier: Modifier = Modifier,
) {
  Scaffold(
    modifier = modifier.fillMaxSize(),
    containerColor = MaterialTheme.colorScheme.background,
    contentWindowInsets = WindowInsets.safeDrawing,
    topBar = {
      CenterAlignedTopAppBar(
        title = { Text("Find a Recipe", fontWeight = FontWeight.Bold) },
        colors =
          TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
          ),
      )
    },
  ) { innerPadding ->
    Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
      OutlinedTextField(
        value = uiState.query,
        onValueChange = onQueryChanged,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 12.dp),
        placeholder = { Text("Search recipes") },
        singleLine = true,
        shape = RoundedCornerShape(18.dp),
      )
      LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        items(uiState.categories) { category ->
          FilterChip(
            selected = uiState.selectedCategory == category,
            onClick = { onCategorySelected(category) },
            label = { Text(category) },
            colors =
              FilterChipDefaults.filterChipColors(
                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
              ),
          )
        }
      }
      Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text("Recipes", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Text("${uiState.recipes.size} results", color = MaterialTheme.colorScheme.onSurfaceVariant)
      }
      if (uiState.recipes.isEmpty()) {
        Column(modifier = Modifier.fillMaxWidth().weight(1f), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
          Text("No recipes found", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
          Text("Try another search or category", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
      } else {
        LazyColumn(
          modifier = Modifier.weight(1f),
          contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 24.dp),
          verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
          items(uiState.recipes, key = Recipe::id) { recipe ->
            RecipeCard(recipe, onFavoriteClick = { onFavoriteClick(recipe.id) })
          }
        }
      }
    }
  }
}

@Composable
private fun RecipeCard(recipe: Recipe, onFavoriteClick: () -> Unit) {
  Card(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
  ) {
    Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
      Image(
        painter = painterResource(recipe.image.drawableRes()),
        contentDescription = recipe.name,
        modifier = Modifier.size(116.dp).clip(RoundedCornerShape(16.dp)),
        contentScale = ContentScale.Crop,
      )
      Spacer(Modifier.width(14.dp))
      Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(recipe.category.uppercase(), color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
        Text(recipe.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, maxLines = 2, overflow = TextOverflow.Ellipsis)
        Text("${recipe.durationMinutes} min  •  ${recipe.calories} cal", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
        Text("★ ${recipe.rating}", color = MaterialTheme.colorScheme.secondary, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
      }
      val action = if (recipe.isFavorite) "Remove from favorites" else "Add to favorites"
      IconButton(onClick = onFavoriteClick, modifier = Modifier.semantics { contentDescription = "$action: ${recipe.name}" }) {
        Text(if (recipe.isFavorite) "♥︎" else "♡", color = MaterialTheme.colorScheme.tertiary, style = MaterialTheme.typography.headlineSmall)
      }
    }
  }
}

private fun RecipeImage.drawableRes(): Int =
  when (this) {
    RecipeImage.AVOCADO_TOAST -> R.drawable.recipe_avocado_toast
    RecipeImage.TOMATO_PASTA -> R.drawable.recipe_tomato_pasta
    RecipeImage.BERRY_BOWL -> R.drawable.recipe_berry_bowl
    RecipeImage.GRILLED_SALMON -> R.drawable.recipe_grilled_salmon
    RecipeImage.MUSHROOM_RISOTTO -> R.drawable.recipe_mushroom_risotto
    RecipeImage.BLUEBERRY_PANCAKES -> R.drawable.recipe_blueberry_pancakes
  }

@Preview(showBackground = true)
@Composable
private fun RecipeSearchScreenPreview() {
  RecipeSearchTheme { RecipeSearchScreen(RecipeSearchUiState(recipes = MockRecipes.items), {}, {}, {}) }
}
