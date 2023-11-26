package io.shodo.pumpkin.monolith.ordering.infra.driven.stock

import io.shodo.pumpkin.annotations.HexagonalArchitecture
import io.shodo.pumpkin.monolith.ordering.domain.Stock
import io.shodo.pumpkin.monolith.ordering.infra.driven.stock.client.StockAccessRestClient
import io.shodo.pumpkin.monolith.ordering.infra.driven.stock.client.toDomain
import io.shodo.pumpkin.monolith.shared.domain.Ingredient
import io.shodo.pumpkin.monolith.shared.domain.Quantity
import org.springframework.stereotype.Component

@Component
@HexagonalArchitecture.RightAdapter
class StockAdapter(private val client: StockAccessRestClient) : Stock {

    override fun hasEnoughOf(ingredient: Ingredient, requiredQuantity: Quantity): Boolean = client.find(ingredient)
        ?.let { it.currentQuantity!!.toDomain() }
        ?.let { it.unitOfMeasure == requiredQuantity.unitOfMeasure && it >= requiredQuantity }
        ?: false

}
