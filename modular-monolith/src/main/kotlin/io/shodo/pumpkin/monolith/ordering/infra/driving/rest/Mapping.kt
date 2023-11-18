package io.shodo.pumpkin.monolith.ordering.infra.driving.rest

import io.shodo.pumpkin.monolith.ordering.domain.Customer
import io.shodo.pumpkin.monolith.ordering.domain.Invoice
import io.shodo.pumpkin.monolith.ordering.domain.Order
import io.shodo.pumpkin.monolith.shared.domain.DrinkName
import org.joda.money.Money

fun OrderRequestBody.toDomain() = Order(
    drink = DrinkName(this.drink!!),
    quantity = this.quantity!!,
    customer = Customer(name = this.customer!!)
)

fun Invoice.toDto() = InvoiceResponseBody(
    totalPrice = this.totalPrice.toDto()
)

private fun Money.toDto() = MoneyField(
    amountMinor = this.amountMinorLong,
    currencyUnit = this.currencyUnit.code,
    scale = this.scale
)
