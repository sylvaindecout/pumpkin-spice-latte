package io.shodo.pumpkin.monolith.ordering.infra.driven.stock.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

@Configuration
class StockAccessRestClientConfig {

    @Bean
    fun stockAccessClient(
        restClientBuilder: RestClient.Builder,
        @Value("\${application.ordering.infra.stock.base-url}") baseUrl: String
    ): RestClient = restClientBuilder.baseUrl(baseUrl).build()

}
