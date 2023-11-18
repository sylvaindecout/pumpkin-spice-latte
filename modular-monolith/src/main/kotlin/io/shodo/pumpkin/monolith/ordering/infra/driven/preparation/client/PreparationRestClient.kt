package io.shodo.pumpkin.monolith.ordering.infra.driven.preparation.client

import io.shodo.pumpkin.monolith.ordering.domain.preparation.PreparationFailureException
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono
import java.util.function.Predicate.not

@Component
class PreparationRestClient(@Qualifier("preparationClient") private val webClient: WebClient) {

    fun queue(requestBody: DrinkRequestBody): Mono<Void> = webClient
        .post()
        .uri("/drinks")
        .contentType(APPLICATION_JSON).bodyValue(requestBody)
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError) { Mono.error(PreparationFailureException("Server responded with client error code ${it.statusCode()}")) }
        .onStatus(HttpStatusCode::is5xxServerError) { Mono.error(PreparationFailureException("Server responded with server error code ${it.statusCode()}")) }
        .bodyToMono<Void>()
        .onErrorMap(not(PreparationFailureException::class::isInstance)) { thr ->
            PreparationFailureException(
                thr.message ?: thr.javaClass.simpleName
            )
        }

}
