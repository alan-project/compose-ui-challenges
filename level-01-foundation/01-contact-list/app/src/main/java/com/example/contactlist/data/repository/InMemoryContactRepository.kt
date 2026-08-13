package com.example.contactlist.data.repository

import com.example.contactlist.data.mock.MockContacts
import com.example.contactlist.data.model.Contact
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InMemoryContactRepository : ContactRepository {
  private val _contacts = MutableStateFlow(MockContacts.items)

  override val contacts: StateFlow<List<Contact>> = _contacts.asStateFlow()

  override fun toggleFavorite(contactId: Long) {
    _contacts.update { contacts ->
      contacts.map { contact ->
        if (contact.id == contactId) {
          contact.copy(isFavorite = !contact.isFavorite)
        } else {
          contact
        }
      }
    }
  }
}
