package com.polarbookshop.catalogservice.demo

import com.polarbookshop.catalogservice.domain.BookRepository
import com.polarbookshop.catalogservice.domain.entities.Book
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Profile
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
@Profile("testdata")
@Suppress("MagicNumber")
class BookDataLoader(
    private val bookRepository: BookRepository
) {
    @EventListener(ApplicationReadyEvent::class)
    fun loadBookTestData() {
        listOf(
            Book("1234567891", "Northern Lights", "Lyra Silverstar", 9.90),
            Book("1234567892", "Polar Journey", "Iorek Polarson", 12.90),
        ).forEach { book ->
            bookRepository.save(book)
        }
    }
}
