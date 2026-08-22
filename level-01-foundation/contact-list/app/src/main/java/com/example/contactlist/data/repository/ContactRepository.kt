package com.example.contactlist.data.repository

import com.example.contactlist.data.model.Contact
import kotlinx.coroutines.flow.Flow

interface ContactRepository {
  val contacts: Flow<List<Contact>>

  fun toggleFavorite(contactId: Long)
}
