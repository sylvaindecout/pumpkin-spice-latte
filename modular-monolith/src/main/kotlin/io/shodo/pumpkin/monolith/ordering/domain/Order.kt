package io.shodo.pumpkin.monolith.ordering.domain

import io.shodo.pumpkin.monolith.shared.domain.DrinkName

data class Order(
    val drink: DrinkName,
    val quantity: Int,
    val customer: Customer
)
