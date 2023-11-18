package io.shodo.pumpkin.monolith.preparation.infra.driven.stock

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class StockUpdateRestClientConfig {

    @Bean
    fun stockUpdateClient(
        webClientBuilder: WebClient.Builder,
        @Value("\${application.preparation.infra.stock.base-url}") baseUrl: String
    ): WebClient = webClientBuilder.baseUrl(baseUrl).build()

}
