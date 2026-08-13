package com.example.contactlist.data.mock

import com.example.contactlist.data.model.Contact

object MockContacts {
  val items =
    listOf(
      Contact(id = 1, name = "Alex Morgan", phoneNumber = "(416) 555-0101", isFavorite = true),
      Contact(id = 2, name = "Mia Thompson", phoneNumber = "(416) 555-0102"),
      Contact(id = 3, name = "Ethan Parker", phoneNumber = "(416) 555-0103"),
      Contact(id = 4, name = "Sofia Bennett", phoneNumber = "(416) 555-0104", isFavorite = true),
      Contact(id = 5, name = "Noah Collins", phoneNumber = "(416) 555-0105"),
      Contact(id = 6, name = "Chloe Mitchell", phoneNumber = "(416) 555-0106"),
      Contact(id = 7, name = "Lucas Reed", phoneNumber = "(416) 555-0107"),
      Contact(id = 8, name = "Ava Foster", phoneNumber = "(416) 555-0108", isFavorite = true),
    )
}
