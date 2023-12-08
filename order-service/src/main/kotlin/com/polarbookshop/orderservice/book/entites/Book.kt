package com.polarbookshop.orderservice.book.entites

data class Book(
    val isbn: String,
    val title: String,
    val author: String,
    val price: Double,
)
