package com.polarbookshop.orderservice.event

data class OrderAcceptedMessage(
    val orderId: Long,
)
