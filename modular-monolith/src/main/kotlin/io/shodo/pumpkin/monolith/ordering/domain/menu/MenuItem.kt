package io.shodo.pumpkin.monolith.ordering.domain.menu

import io.shodo.pumpkin.monolith.shared.domain.DrinkName
import io.shodo.pumpkin.monolith.shared.domain.Recipe
import org.joda.money.Money

data class MenuItem(
    val name: DrinkName,
    val unitPrice: Money,
    val recipe: Recipe
)
