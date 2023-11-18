package io.shodo.pumpkin.monolith.ordering.infra.driven.stock.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class StockAccessRestClientConfig {

    @Bean
    fun stockAccessClient(
        webClientBuilder: WebClient.Builder,
        @Value("\${application.ordering.infra.stock.base-url}") baseUrl: String
    ): WebClient = webClientBuilder.baseUrl(baseUrl).build()

}
