package com.example.contactlist.ui.contactlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactlist.data.model.Contact
import com.example.contactlist.data.repository.ContactRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class ContactListUiState(
  val contacts: List<Contact> = emptyList(),
)

class ContactListViewModel(
  private val contactRepository: ContactRepository,
) : ViewModel() {
  val uiState: StateFlow<ContactListUiState> =
    contactRepository.contacts
      .map(::ContactListUiState)
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ContactListUiState(),
      )

  fun onFavoriteClick(contactId: Long) {
    contactRepository.toggleFavorite(contactId)
  }
}
