package com.polarbookshop.catalogservice.persistence

import com.polarbookshop.catalogservice.domain.entities.Book
import com.polarbookshop.catalogservice.domain.BookRepository
import org.springframework.stereotype.Repository

@Repository
class InMemoryBookRepository : BookRepository {
    companion object {
        private val books = mutableMapOf<String, Book>()
    }
    override fun findAll(): List<Book> = books.values.toList()

    override fun findByIsbn(isbn: String): Book? = books[isbn]

    override fun existsByIsbn(isbn: String): Boolean = (books[isbn] != null)

    override fun save(book: Book): Book = book.apply {books[isbn] = this}

    override fun deleteByIsbn(isbn: String) {
        books.remove(isbn)
    }
}
