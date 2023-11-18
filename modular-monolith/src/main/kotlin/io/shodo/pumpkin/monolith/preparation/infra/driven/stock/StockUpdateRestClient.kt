package io.shodo.pumpkin.monolith.preparation.infra.driven.stock

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono
import java.util.function.Predicate

@Component
class StockUpdateRestClient(@Qualifier("stockUpdateClient") private val webClient: WebClient) {

    fun notifyConsumption(requestBody: StockUpdateRequestBody): Mono<Void> = webClient
        .post()
        .uri("/use")
        .contentType(APPLICATION_JSON).bodyValue(requestBody)
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError) { Mono.error(StockUpdateFailureException("Server responded with client error code ${it.statusCode()}")) }
        .onStatus(HttpStatusCode::is5xxServerError) { Mono.error(StockUpdateFailureException("Server responded with server error code ${it.statusCode()}")) }
        .bodyToMono<Void>()
        .onErrorMap(Predicate.not(StockUpdateFailureException::class::isInstance)) { thr ->
            StockUpdateFailureException(
                thr.message ?: thr.javaClass.simpleName
            )
        }

}
