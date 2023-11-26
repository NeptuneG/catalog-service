package com.polarbookshop.catalogservice.domain.exceptions

class BookAlreadyExistsException(isbn: String): Exception("A book with ISBN $isbn already exists.")
