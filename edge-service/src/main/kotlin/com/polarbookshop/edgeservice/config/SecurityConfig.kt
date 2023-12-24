package com.polarbookshop.edgeservice.config

import org.springframework.context.annotation.Bean
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler

@EnableWebFluxSecurity
class SecurityConfig {
    @Bean
    fun securityWebFilterChain(
        http: ServerHttpSecurity,
        clientRegistrationRepository: ReactiveClientRegistrationRepository,
    ): SecurityWebFilterChain {
        return http
            .authorizeExchange { exchange ->
                exchange.anyExchange().authenticated()
            }
            .oauth2Login(Customizer.withDefaults())
            .logout { logout ->
                logout.logoutSuccessHandler(oidcLogoutSuccessHandler(clientRegistrationRepository))
            }
            .build()
    }

    private fun oidcLogoutSuccessHandler(
        clientRegistrationRepository: ReactiveClientRegistrationRepository,
    ): ServerLogoutSuccessHandler = OidcClientInitiatedServerLogoutSuccessHandler(clientRegistrationRepository).apply {
        setPostLogoutRedirectUri("{baseUrl}")
    }
}