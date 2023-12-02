package com.polarbookshop.catalogservice.domain

import com.polarbookshop.catalogservice.domain.entities.Book
import com.polarbookshop.catalogservice.domain.exceptions.BookAlreadyExistsException
import com.polarbookshop.catalogservice.domain.exceptions.BookNotFoundException
import org.springframework.stereotype.Service


@Service
class BookService(
    private val bookRepository: BookRepository
) {
    fun viewBookList(): List<Book> = bookRepository.findAll().toList()

    fun viewBookDetails(isbn: String): Book = bookRepository.findByIsbn(isbn) ?: throw BookNotFoundException(isbn)

    fun addBookToCatalog(book: Book): Book {
        if (bookRepository.existsByIsbn(book.isbn)) {
            throw BookAlreadyExistsException(book.isbn)
        }
        return bookRepository.save(book)
    }

    fun removeBookFromCatalog(isbn: String) = bookRepository.deleteByIsbn(isbn)

    fun editBookDetails(isbn: String, book: Book): Book {
        return bookRepository.findByIsbn(isbn)
            ?.let { existingBook ->
                val bookToUpdate = book.copy(
                    id = existingBook.id,
                    isbn = existingBook.isbn,
                    createdDate = existingBook.createdDate,
                    lastModifiedDate = existingBook.lastModifiedDate,
                    version = existingBook.version,
                )
                bookRepository.save(bookToUpdate)
            } ?: addBookToCatalog(book)
    }
}
