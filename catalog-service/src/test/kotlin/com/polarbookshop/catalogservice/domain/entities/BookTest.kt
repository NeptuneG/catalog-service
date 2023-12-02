package com.polarbookshop.catalogservice.domain.entities

import jakarta.validation.Validation
import jakarta.validation.Validator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BookTest {
    companion object {
        private val validator: Validator = Validation.buildDefaultValidatorFactory().validator
    }

    @Test
    fun whenAllFieldsCorrectThenValidationSucceeds() {
        val book = Book("1234567890", "Title", "Author", 9.90, "Polarsophia")
        val violation = validator.validate(book)

        assertThat(violation).isEmpty()
    }


    @Test
    fun whenIsbnDefinedButIncorrectThenValidationFails() {
        val book = Book("a234567890", "Title", "Author", 9.90, "Polarsophia")
        val violations = validator.validate(book)

        assertThat(violations).hasSize(1)
        assertThat(violations.iterator().next().message).isEqualTo("The ISBN format must be valid.");
    }
}
