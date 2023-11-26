package io.shodo.pumpkin.monolith.ordering.infra.driven.preparation

import io.shodo.pumpkin.annotations.HexagonalArchitecture
import io.shodo.pumpkin.monolith.ordering.domain.preparation.Drink
import io.shodo.pumpkin.monolith.ordering.domain.preparation.DrinkPreparation
import io.shodo.pumpkin.monolith.ordering.infra.driven.preparation.client.PreparationRestClient
import io.shodo.pumpkin.monolith.ordering.infra.driven.preparation.client.toDto
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
@HexagonalArchitecture.RightAdapter
class DrinkPreparationAdapter(private val client: PreparationRestClient) : DrinkPreparation {

    private val logger = KotlinLogging.logger {}

    override fun prepare(drink: Drink) {
        client.queue(drink.toDto())
            .doOnSuccess { logger.info { "Drink $drink sent for preparation" } }
            .doOnError { ex -> logger.error("Failed to send drink $drink for preparation", ex) }
            .block()
    }

}
