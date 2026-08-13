package com.example.contactlist.ui.contactlist

import com.example.contactlist.data.repository.InMemoryContactRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ContactListViewModelTest {
  @Test
  fun uiState_containsRepositoryContacts() = runTest {
    val repository = InMemoryContactRepository()
    val viewModel = ContactListViewModel(repository)

    assertEquals(repository.contacts.value, viewModel.uiState.value.contacts)
  }

  @Test
  fun onFavoriteClick_togglesSelectedContact() = runTest {
    val repository = InMemoryContactRepository()
    val viewModel = ContactListViewModel(repository)
    val contact = viewModel.uiState.value.contacts.first { !it.isFavorite }

    assertFalse(contact.isFavorite)

    viewModel.onFavoriteClick(contact.id)

    assertTrue(repository.contacts.value.first { it.id == contact.id }.isFavorite)
  }
}
