package com.example.contactlist.ui.contactlist

import androidx.lifecycle.ViewModel
import com.example.contactlist.data.repository.ContactRepository

class ContactListViewModel(
  private val contactRepository: ContactRepository,
) : ViewModel() {
}
