package com.example.contactlist.data.model

data class Contact(
  val id: Long,
  val name: String,
  val phoneNumber: String,
  val isFavorite: Boolean = false,
)
