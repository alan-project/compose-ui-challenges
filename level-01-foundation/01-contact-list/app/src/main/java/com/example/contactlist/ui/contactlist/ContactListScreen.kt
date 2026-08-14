package com.example.contactlist.ui.contactlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.contactlist.data.mock.MockContacts
import com.example.contactlist.data.model.Contact
import com.example.contactlist.theme.AvatarCerulean
import com.example.contactlist.theme.AvatarCobalt
import com.example.contactlist.theme.AvatarDarkContent
import com.example.contactlist.theme.AvatarEmerald
import com.example.contactlist.theme.AvatarLightContent
import com.example.contactlist.theme.AvatarMagenta
import com.example.contactlist.theme.AvatarSunflower
import com.example.contactlist.theme.AvatarTangerine
import com.example.contactlist.theme.ContactListTheme

@Composable
fun ContactListRoute(
  viewModel: ContactListViewModel,
  modifier: Modifier = Modifier,
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  ContactListScreen(
    contacts = uiState.contacts,
    onFavoriteClick = viewModel::onFavoriteClick,
    modifier = modifier,
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListScreen(
  contacts: List<Contact>,
  onFavoriteClick: (Long) -> Unit,
  modifier: Modifier = Modifier,
) {
  Scaffold(
    modifier = modifier.fillMaxSize(),
    containerColor = MaterialTheme.colorScheme.background,
    contentWindowInsets = WindowInsets.safeDrawing,
    topBar = {
      CenterAlignedTopAppBar(
        colors =
          TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.primary,
          ),
        title = {
          Text(
            text = "Contacts",
            fontWeight = FontWeight.Bold,
          )
        },
      )
    },
  ) { innerPadding ->
    LazyColumn(
      modifier = Modifier.fillMaxSize().padding(innerPadding),
      contentPadding = PaddingValues(vertical = 8.dp),
    ) {
      items(
        items = contacts,
        key = Contact::id,
      ) { contact ->
        ContactRow(
          contact = contact,
          onFavoriteClick = { onFavoriteClick(contact.id) },
        )
        HorizontalDivider(
          modifier = Modifier.padding(start = 80.dp),
          color = MaterialTheme.colorScheme.outlineVariant,
        )
      }
    }
  }
}

@Composable
private fun ContactRow(
  contact: Contact,
  onFavoriteClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val avatarPalette = contactAvatarPalette(contact.id)

  Row(
    modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Box(
      modifier =
        Modifier.size(48.dp)
          .clip(CircleShape)
          .background(avatarPalette.background),
      contentAlignment = Alignment.Center,
    ) {
      Text(
        text = contact.name.firstOrNull()?.toString().orEmpty(),
        color = avatarPalette.content,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
      )
    }

    Spacer(modifier = Modifier.width(16.dp))

    Column(
      modifier = Modifier.weight(1f),
      verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
      Text(
        text = contact.name,
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.SemiBold,
      )
      Text(
        text = contact.phoneNumber,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        style = MaterialTheme.typography.bodyMedium,
      )
    }

    val favoriteAction = if (contact.isFavorite) "Remove from favorites" else "Add to favorites"
    IconButton(
      onClick = onFavoriteClick,
      modifier =
        Modifier
          .clip(CircleShape)
          .background(
            if (contact.isFavorite) {
              MaterialTheme.colorScheme.tertiaryContainer
            } else {
              MaterialTheme.colorScheme.surfaceVariant
            },
          ).semantics { contentDescription = "$favoriteAction: ${contact.name}" },
    ) {
      Text(
        text = if (contact.isFavorite) "★" else "☆",
        color =
          if (contact.isFavorite) {
            MaterialTheme.colorScheme.secondary
          } else {
            MaterialTheme.colorScheme.onSurfaceVariant
          },
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Black,
      )
    }
  }
}

private data class ContactAvatarPalette(
  val background: Color,
  val content: Color,
)

private val contactAvatarPalettes =
  listOf(
    ContactAvatarPalette(AvatarCerulean, AvatarLightContent),
    ContactAvatarPalette(AvatarTangerine, AvatarLightContent),
    ContactAvatarPalette(AvatarSunflower, AvatarDarkContent),
    ContactAvatarPalette(AvatarCobalt, AvatarLightContent),
    ContactAvatarPalette(AvatarMagenta, AvatarLightContent),
    ContactAvatarPalette(AvatarEmerald, AvatarLightContent),
  )

private fun contactAvatarPalette(contactId: Long): ContactAvatarPalette {
  val index = ((contactId - 1).mod(contactAvatarPalettes.size.toLong())).toInt()
  return contactAvatarPalettes[index]
}

@Preview(showBackground = true)
@Composable
private fun ContactListScreenPreview() {
  ContactListTheme {
    ContactListScreen(
      contacts = MockContacts.items,
      onFavoriteClick = {},
    )
  }
}
