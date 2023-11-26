package com.polarbookshop.catalogservice.domain

import com.polarbookshop.catalogservice.domain.entities.Book

interface BookRepository {
    fun findAll(): List<Book>
    fun findByIsbn(isbn: String): Book?
    fun existsByIsbn(isbn: String): Boolean
    fun save(book: Book): Book
    fun deleteByIsbn(isbn: String)
}
