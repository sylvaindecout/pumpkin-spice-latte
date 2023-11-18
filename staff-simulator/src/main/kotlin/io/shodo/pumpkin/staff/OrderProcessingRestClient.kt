package io.shodo.pumpkin.staff

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono
import java.util.function.Predicate.not

@Component
class OrderProcessingRestClient(@Qualifier("orderProcessingClient") private val webClient: WebClient) {

    fun processNextOrder(): Mono<Void> = webClient
        .post()
        .uri("/drinks/take")
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError) { Mono.error(OrderProcessingException("Server responded with client error code ${it.statusCode()}")) }
        .onStatus(HttpStatusCode::is5xxServerError) { Mono.error(OrderProcessingException("Server responded with server error code ${it.statusCode()}")) }
        .bodyToMono<Void>()
        .onErrorMap(not(OrderProcessingException::class::isInstance)) { thr ->
            OrderProcessingException(
                thr.message ?: thr.javaClass.simpleName
            )
        }

}
