package io.shodo.pumpkin.monolith.ordering.infra.driven.catalogue.client

import io.shodo.pumpkin.monolith.ordering.domain.catalogue.CatalogueAccessFailureException
import io.shodo.pumpkin.monolith.shared.domain.DrinkName
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
class CatalogueAccessRestClient(
    @Qualifier("catalogueAccessClient") private val webClient: WebClient,
    private val validator: Validator
) {

    fun find(drink: DrinkName): CatalogueItemResponseBody? = webClient
        .get()
        .uri("/drinks/{name}", drink.value)
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError) { Mono.error(CatalogueAccessFailureException("Server responded with client error code ${it.statusCode()}")) }
        .onStatus(HttpStatusCode::is5xxServerError) { Mono.error(CatalogueAccessFailureException("Server responded with server error code ${it.statusCode()}")) }
        .bodyToMono<CatalogueItemResponseBody>()
        .single()
        .onErrorMap(not(CatalogueAccessFailureException::class::isInstance)) { thr ->
            CatalogueAccessFailureException(
                thr.message ?: thr.javaClass.simpleName
            )
        }
        .flatMap { requireValid(it) }
        .block()

    private fun requireValid(dto: CatalogueItemResponseBody) = validator.validate(dto)
        .takeIf { it.isNotEmpty() }
        ?.let { Mono.error(CatalogueAccessFailureException("Server responded with unexpected format - ${it.format()}")) }
        ?: Mono.just(dto)

}
