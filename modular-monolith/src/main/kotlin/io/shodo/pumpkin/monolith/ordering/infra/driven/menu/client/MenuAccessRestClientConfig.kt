package io.shodo.pumpkin.monolith.ordering.infra.driven.menu.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

@Configuration
class MenuAccessRestClientConfig {

    @Bean
    fun menuAccessClient(
        restClientBuilder: RestClient.Builder,
        @Value("\${application.ordering.infra.menu.base-url}") baseUrl: String
    ): RestClient = restClientBuilder.baseUrl(baseUrl).build()

}
