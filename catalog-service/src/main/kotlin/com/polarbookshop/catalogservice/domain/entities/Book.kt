package com.polarbookshop.catalogservice.domain.entities

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Positive
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import java.time.Instant

data class Book(
    @JsonProperty("id")
    @field:Id
    val id: Long?,

    @JsonProperty("isbn")
    @field:NotBlank(message = "The book ISBN must be defined.")
    @field:Pattern(
        regexp = "^([0-9]{10}|[0-9]{13})$",
        message = "The ISBN format must be valid."
    )
    val isbn: String,

    @JsonProperty("title")
    @field:NotBlank(message = "The book title must be defined.")
    val title: String,

    @JsonProperty("author")
    @field:NotBlank(message = "The book author must be defined.")
    val author: String,

    @JsonProperty("price")
    @field:NotNull(message = "The book price must be defined.")
    @field:Positive(message = "The book price must be greater than zero.")
    val price: Double,

    @JsonProperty("publisher")
    val publisher: String,

    @JsonProperty("createdDate")
    @CreatedDate
    val createdDate: Instant?,

    @JsonProperty("lastModifiedDate")
    @LastModifiedDate
    val lastModifiedDate: Instant?,

    @JsonProperty("version")
    @field:Version
    val version: Int
) {
    constructor(
        isbn: String,
        title: String,
        author: String,
        price: Double,
        publisher: String,
        now: Instant = Instant.now()
    ) : this(null, isbn, title, author, price, publisher, now, now, 0)
}
