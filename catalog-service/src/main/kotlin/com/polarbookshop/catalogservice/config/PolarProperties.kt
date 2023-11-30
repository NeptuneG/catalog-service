package com.polarbookshop.catalogservice.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "polar")
data class PolarProperties(
    /**
     * A message to welcome users.
     */
    val greeting: String
)
