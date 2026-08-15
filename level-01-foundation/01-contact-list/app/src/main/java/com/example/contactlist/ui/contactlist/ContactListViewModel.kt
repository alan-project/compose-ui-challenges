package com.example.contactlist.ui.contactlist

import androidx.lifecycle.ViewModel
import com.example.contactlist.data.model.Contact
import com.example.contactlist.data.repository.ContactRepository

data class ContactListUiState(
    val contacts: List<Contact> = emptyList()
)
class ContactListViewModel(
  private val contactRepository: ContactRepository,
) : ViewModel() {
}
