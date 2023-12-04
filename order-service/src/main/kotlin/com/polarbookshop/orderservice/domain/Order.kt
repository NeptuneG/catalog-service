package com.polarbookshop.orderservice.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

@Table("orders")
data class Order(
    @Id
    val id: Long?,
    val bookIsbn: String,
    val bookName: String?,
    val bookPrice: Double?,
    val quantity: Int,
    val status: OrderStatus,
    @CreatedDate
    val createdDate: Instant,
    @LastModifiedDate
    val lastModifiedDate: Instant,
    @Version
    val version: Int,
) {
    constructor(
        bookIsbn: String,
        bookName: String?,
        bookPrice: Double?,
        quantity: Int,
        status: OrderStatus,
        now: Instant = Instant.now()
    ) : this(null, bookIsbn, bookName, bookPrice, quantity, status, now, now, 0)

    companion object {
        fun buildRejected(bookIsbn: String, quantity: Int): Order =
            Order(bookIsbn, null, null, quantity, OrderStatus.REJECTED)
    }
}
