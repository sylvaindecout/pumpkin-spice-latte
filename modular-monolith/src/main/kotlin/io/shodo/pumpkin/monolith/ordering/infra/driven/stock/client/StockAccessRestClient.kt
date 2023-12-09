package io.shodo.pumpkin.monolith.ordering.infra.driven.stock.client

import io.shodo.pumpkin.monolith.ordering.domain.StockAccessFailureException
import io.shodo.pumpkin.monolith.shared.domain.Ingredient
import io.shodo.pumpkin.monolith.shared.validation.format
import jakarta.validation.Validator
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class StockAccessRestClient(
    @Qualifier("stockAccessClient") private val restClient: RestClient,
    private val validator: Validator
) {

    fun find(ingredient: Ingredient): StockItemResponseBody = try {
        restClient.get().uri("/ingredients/{name}", ingredient.name)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError) { _, response -> throw StockAccessFailureException("Server responded with client error code ${response.statusCode}") }
            .onStatus(HttpStatusCode::is5xxServerError) { _, response -> throw StockAccessFailureException("Server responded with server error code ${response.statusCode}") }
            .body(StockItemResponseBody::class.java)!!
            .let { requireValid(it) }
    } catch (thr: Throwable) {
        throw thr.takeIf { thr is StockAccessFailureException }
            ?: StockAccessFailureException(thr.message ?: thr.javaClass.simpleName)
    }

    private fun requireValid(dto: StockItemResponseBody) = validator.validate(dto)
        .takeIf { it.isNotEmpty() }
        ?.let { throw StockAccessFailureException("Server responded with unexpected format - ${it.format()}") }
        ?: dto

}
