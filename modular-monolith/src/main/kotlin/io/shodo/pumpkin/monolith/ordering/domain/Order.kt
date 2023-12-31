package io.shodo.pumpkin.monolith.ordering.domain

import io.shodo.pumpkin.annotations.DomainDrivenDesign
import io.shodo.pumpkin.monolith.shared.domain.DrinkName

@DomainDrivenDesign.ValueObject
data class Order(
    val drink: DrinkName,
    val quantity: Int,
    val customer: Customer
)
