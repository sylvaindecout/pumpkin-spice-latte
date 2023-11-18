package io.shodo.pumpkin.monolith.ordering.domain.catalogue

import io.shodo.pumpkin.monolith.shared.domain.DrinkName
import io.shodo.pumpkin.monolith.shared.domain.Recipe
import org.joda.money.Money

data class CatalogueItem(
    val name: DrinkName,
    val unitPrice: Money,
    val recipe: Recipe
)
