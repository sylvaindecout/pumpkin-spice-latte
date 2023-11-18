package io.shodo.pumpkin.staff

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class StaffSimulatorConfig {

    @Bean
    fun orderProcessingClient(
        webClientBuilder: WebClient.Builder,
        @Value("\${application.staff-simulator.infra.preparation.base-url}") baseUrl: String
    ): WebClient = webClientBuilder.baseUrl(baseUrl).build()

}
