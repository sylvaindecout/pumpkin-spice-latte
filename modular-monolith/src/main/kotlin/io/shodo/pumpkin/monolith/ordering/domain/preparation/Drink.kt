package io.shodo.pumpkin.monolith.ordering.domain.preparation

import io.shodo.pumpkin.annotations.DomainDrivenDesign
import io.shodo.pumpkin.monolith.ordering.domain.Customer
import io.shodo.pumpkin.monolith.shared.domain.DrinkName
import io.shodo.pumpkin.monolith.shared.domain.Recipe

@DomainDrivenDesign.ValueObject
data class Drink(
    val name: DrinkName,
    val recipe: Recipe,
    val customer: Customer
)
