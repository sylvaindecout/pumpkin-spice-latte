package io.shodo.pumpkin.monolith.preparation.infra.driven.stock

import io.shodo.pumpkin.annotations.HexagonalArchitecture
import io.shodo.pumpkin.monolith.preparation.domain.Stock
import io.shodo.pumpkin.monolith.shared.domain.Ingredient
import io.shodo.pumpkin.monolith.shared.domain.Quantity
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
@HexagonalArchitecture.RightAdapter
class StockUpdateAdapter(private val client: StockUpdateRestClient) : Stock {

    private val logger = KotlinLogging.logger {}

    override fun notifyConsumption(ingredient: Ingredient, usedQuantity: Quantity) {
        client.notifyConsumption(StockUpdateRequestBody(ingredient.name, usedQuantity.toDto()))
            .doOnSuccess { logger.info { "Notified consumption of $usedQuantity $ingredient" } }
            .doOnError { ex -> logger.error("Failed to notify consumption of $usedQuantity $ingredient", ex) }
            .block()
    }

}
