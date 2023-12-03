package io.shodo.pumpkin.monolith.ordering.infra.driven.menu.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {

    @Bean
    fun menuAccessClient(
        webClientBuilder: WebClient.Builder,
        @Value("\${application.ordering.infra.menu.base-url}") baseUrl: String
    ): WebClient = webClientBuilder.baseUrl(baseUrl).build()

}
