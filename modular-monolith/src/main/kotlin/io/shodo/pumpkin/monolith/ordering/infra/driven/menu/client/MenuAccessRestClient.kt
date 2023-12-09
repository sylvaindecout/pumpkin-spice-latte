package io.shodo.pumpkin.monolith.ordering.infra.driven.menu.client

import io.shodo.pumpkin.monolith.ordering.domain.menu.MenuAccessFailureException
import io.shodo.pumpkin.monolith.shared.domain.DrinkName
import io.shodo.pumpkin.monolith.shared.validation.format
import jakarta.validation.Validator
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class MenuAccessRestClient(
    @Qualifier("menuAccessClient") private val restClient: RestClient,
    private val validator: Validator
) {

    fun find(drink: DrinkName): MenuItemResponseBody = try {
        restClient.get().uri("/drinks/{name}", drink.value)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError) { _, response -> throw MenuAccessFailureException("Server responded with client error code ${response.statusCode}") }
            .onStatus(HttpStatusCode::is5xxServerError) { _, response -> throw MenuAccessFailureException("Server responded with server error code ${response.statusCode}") }
            .body(MenuItemResponseBody::class.java)!!
            .let { requireValid(it) }
    } catch (thr: Throwable) {
        throw thr.takeIf { thr is MenuAccessFailureException }
            ?: MenuAccessFailureException(thr.message ?: thr.javaClass.simpleName)
    }

    private fun requireValid(dto: MenuItemResponseBody) = validator.validate(dto)
        .takeIf { it.isNotEmpty() }
        ?.let { throw MenuAccessFailureException("Server responded with unexpected format - ${it.format()}") }
        ?: dto

}
