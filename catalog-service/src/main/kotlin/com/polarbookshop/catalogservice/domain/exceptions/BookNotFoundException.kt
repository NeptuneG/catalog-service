package com.polarbookshop.catalogservice.domain.exceptions

class BookNotFoundException(isbn: String): Exception("The book with ISBN $isbn was not found.")
