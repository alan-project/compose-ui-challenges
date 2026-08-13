package com.example.contactlist.data.repository

import com.example.contactlist.data.model.Contact
import kotlinx.coroutines.flow.StateFlow

interface ContactRepository {
  val contacts: StateFlow<List<Contact>>

  fun toggleFavorite(contactId: Long)
}
