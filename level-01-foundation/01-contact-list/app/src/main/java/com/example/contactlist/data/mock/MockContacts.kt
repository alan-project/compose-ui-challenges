package com.example.contactlist.data.mock

import com.example.contactlist.data.model.Contact

object MockContacts {
  val items =
    listOf(
      Contact(id = 1, name = "김민준", phoneNumber = "010-1234-5678", isFavorite = true),
      Contact(id = 2, name = "이서연", phoneNumber = "010-2345-6789"),
      Contact(id = 3, name = "박지훈", phoneNumber = "010-3456-7890"),
      Contact(id = 4, name = "최유진", phoneNumber = "010-4567-8901", isFavorite = true),
      Contact(id = 5, name = "정현우", phoneNumber = "010-5678-9012"),
      Contact(id = 6, name = "강수빈", phoneNumber = "010-6789-0123"),
      Contact(id = 7, name = "윤도현", phoneNumber = "010-7890-1234"),
      Contact(id = 8, name = "한예린", phoneNumber = "010-8901-2345", isFavorite = true),
    )
}
