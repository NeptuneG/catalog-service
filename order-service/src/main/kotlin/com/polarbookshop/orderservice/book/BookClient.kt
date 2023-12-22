package com.polarbookshop.orderservice.book

import com.polarbookshop.orderservice.book.entites.Book
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Mono
import reactor.util.retry.Retry
import java.time.Duration

@Component
class BookClient(
    private val webClient: WebClient
) {
    companion object {
        private const val BOOKS_ROOT_API = "/books/"
        private val TIME_OUT = Duration.ofSeconds(3)
        private const val MAX_RETRY_ATTEMPTS = 3L
        private val MIN_RETRY_BACKOFF = Duration.ofMillis(100)
        private val RETRY_BACKOFF = Retry.backoff(MAX_RETRY_ATTEMPTS, MIN_RETRY_BACKOFF)
    }

    fun getBookByIsbn(isbn: String): Mono<Book> {
        return webClient.get().uri(BOOKS_ROOT_API + isbn)
            .retrieve().bodyToMono(Book::class.java)
            .timeout(TIME_OUT, Mono.empty())
            .onErrorResume(WebClientResponseException::class.java) {
                Mono.empty()
            }
            .retryWhen(RETRY_BACKOFF)
            .onErrorResume {
                Mono.empty()
            }
    }
}
