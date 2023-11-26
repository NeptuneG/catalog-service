package com.polarbookshop.catalogservice.web

import com.polarbookshop.catalogservice.web.viewobjects.Response
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController {
    @GetMapping("/")
    fun getGreeting(): Response {
        return Response("Welcome to the book catalog!")
    }
}
