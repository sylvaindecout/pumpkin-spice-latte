package io.shodo.pumpkin.monolith.ordering.domain

import io.shodo.pumpkin.monolith.shared.domain.DrinkName
import org.joda.money.Money

data class Invoice(
    val drink: DrinkName,
    val quantity: Int,
    val unitPrice: Money
) {

    val totalPrice: Money by lazy { unitPrice.multipliedBy(quantity.toLong()) }

}
