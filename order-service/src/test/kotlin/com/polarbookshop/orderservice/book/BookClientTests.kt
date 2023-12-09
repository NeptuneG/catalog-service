package com.polarbookshop.orderservice.book

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import reactor.test.StepVerifier

class BookClientTests{
    private lateinit var mockWebServer: MockWebServer
    private lateinit var bookClient: BookClient

    @BeforeEach
    fun setup() {
        mockWebServer = MockWebServer().apply { start() }
        val url = mockWebServer.url("/").toUri().toString()
        bookClient = WebClient.builder().baseUrl(url).build().let { BookClient(it) }
    }

    @AfterEach
    fun clean() {
        mockWebServer.shutdown()
    }

    @Test
    fun whenBookExistsThenReturnBook(): Unit = runBlocking {
        val bookIsbn = "1234567890"
        val response = MockResponse().apply {
            addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            setBody("""
                {
                  "isbn": "$bookIsbn",
                  "title": "Title",
                  "author": "Author",
                  "price": 9.90,
                  "publisher": "Polarsophia"
                }
            """.trimIndent())
        }

        mockWebServer.enqueue(response)
        val book = bookClient.getBookByIsbn(bookIsbn)

        StepVerifier.create(book).expectNextMatches { actualBook ->
            actualBook.isbn == bookIsbn
        }.verifyComplete()
    }
}
