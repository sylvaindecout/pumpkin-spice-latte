package io.shodo.pumpkin.monolith.preparation.domain

import io.shodo.pumpkin.annotations.DomainDrivenDesign
import io.shodo.pumpkin.monolith.shared.domain.DrinkName
import io.shodo.pumpkin.monolith.shared.domain.Recipe

@DomainDrivenDesign.ValueObject
data class Drink(
    val name: DrinkName,
    val recipe: Recipe,
    val customer: Customer
)
