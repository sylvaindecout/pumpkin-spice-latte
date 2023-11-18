package io.shodo.pumpkin.monolith.ordering.infra.driven.stock.client

import io.shodo.pumpkin.monolith.ordering.domain.StockAccessFailureException
import io.shodo.pumpkin.monolith.shared.domain.Ingredient
import io.shodo.pumpkin.monolith.shared.validation.format
import jakarta.validation.Validator
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono
import java.util.function.Predicate.not

@Component
class StockAccessRestClient(
    @Qualifier("stockAccessClient") private val webClient: WebClient,
    private val validator: Validator
) {

    fun find(ingredient: Ingredient): StockItemResponseBody? = webClient
        .get()
        .uri("/ingredients/{name}", ingredient.name)
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError) { Mono.error(StockAccessFailureException("Server responded with client error code ${it.statusCode()}")) }
        .onStatus(HttpStatusCode::is5xxServerError) { Mono.error(StockAccessFailureException("Server responded with server error code ${it.statusCode()}")) }
        .bodyToMono<StockItemResponseBody>()
        .single()
        .onErrorMap(not(StockAccessFailureException::class::isInstance)) { thr ->
            StockAccessFailureException(
                thr.message ?: thr.javaClass.simpleName
            )
        }
        .flatMap { requireValid(it) }
        .block()

    private fun requireValid(dto: StockItemResponseBody) = validator.validate(dto)
        .takeIf { it.isNotEmpty() }
        ?.let { Mono.error(StockAccessFailureException("Server responded with unexpected format - ${it.format()}")) }
        ?: Mono.just(dto)

}
