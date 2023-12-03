package io.shodo.pumpkin.monolith.ordering.infra.driven.menu.client

import io.shodo.pumpkin.monolith.ordering.domain.menu.MenuAccessFailureException
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
class MenuAccessRestClient(
    @Qualifier("menuAccessClient") private val webClient: WebClient,
    private val validator: Validator
) {

    fun find(drink: DrinkName): MenuItemResponseBody? = webClient
        .get()
        .uri("/drinks/{name}", drink.value)
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError) { Mono.error(MenuAccessFailureException("Server responded with client error code ${it.statusCode()}")) }
        .onStatus(HttpStatusCode::is5xxServerError) { Mono.error(MenuAccessFailureException("Server responded with server error code ${it.statusCode()}")) }
        .bodyToMono<MenuItemResponseBody>()
        .single()
        .onErrorMap(not(MenuAccessFailureException::class::isInstance)) { thr ->
            MenuAccessFailureException(
                thr.message ?: thr.javaClass.simpleName
            )
        }
        .flatMap { requireValid(it) }
        .block()

    private fun requireValid(dto: MenuItemResponseBody) = validator.validate(dto)
        .takeIf { it.isNotEmpty() }
        ?.let { Mono.error(MenuAccessFailureException("Server responded with unexpected format - ${it.format()}")) }
        ?: Mono.just(dto)

}
