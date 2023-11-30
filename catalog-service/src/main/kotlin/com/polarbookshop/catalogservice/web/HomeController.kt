package com.polarbookshop.catalogservice.web

import com.polarbookshop.catalogservice.config.PolarProperties
import com.polarbookshop.catalogservice.web.viewobjects.Response
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController(
    private val polarProperties: PolarProperties
) {
    @GetMapping("/")
    fun getGreeting(): Response {
        return Response(polarProperties.greeting)
    }
}
