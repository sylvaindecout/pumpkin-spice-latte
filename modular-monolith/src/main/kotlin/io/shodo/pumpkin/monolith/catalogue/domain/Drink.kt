package io.shodo.pumpkin.monolith.catalogue.domain

import io.shodo.pumpkin.monolith.shared.domain.DrinkName
import io.shodo.pumpkin.monolith.shared.domain.Recipe
import org.joda.money.Money

data class Drink(
    val name: DrinkName,
    val unitPrice: Money,
    val recipe: Recipe
)
