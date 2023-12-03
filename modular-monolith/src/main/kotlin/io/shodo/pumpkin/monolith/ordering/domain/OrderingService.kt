package io.shodo.pumpkin.monolith.ordering.domain

import io.shodo.pumpkin.monolith.ordering.domain.menu.Menu
import io.shodo.pumpkin.monolith.ordering.domain.menu.MenuItem
import io.shodo.pumpkin.monolith.ordering.domain.preparation.Drink
import io.shodo.pumpkin.monolith.ordering.domain.preparation.DrinkPreparation
import io.shodo.pumpkin.monolith.shared.domain.DrinkName

class OrderingService(
    val preparation: DrinkPreparation,
    val menu: Menu,
    val stock: Stock
) : CustomerOrderHandler {

    override fun process(order: Order): Invoice = findInMenu(order.drink)
        .also { failOnUnavailableIngredient(it, order.quantity) }
        .also { sendToPreparation(it, order) }
        .toInvoiceWith(order.quantity)

    private fun findInMenu(drink: DrinkName) = menu.find(drink) ?: throw UnknownDrinkException(drink)

    private fun sendToPreparation(menuItem: MenuItem, order: Order) = menuItem
        .asDrinkFor(order.customer)
        .let { drink -> repeat(order.quantity) { preparation.prepare(drink) } }

    private fun failOnUnavailableIngredient(menuItem: MenuItem, orderedQuantity: Int) {
        menuItem.recipe
            .asMap().toList()
            .map { it.first to it.second * orderedQuantity }
            .find { !stock.hasEnoughOf(it.first, it.second) }
            ?.let { throw UnavailableIngredientException(it.first) }
    }

    private fun MenuItem.asDrinkFor(customer: Customer) = Drink(
        name = this.name,
        recipe = this.recipe,
        customer = customer
    )

    private fun MenuItem.toInvoiceWith(quantity: Int): Invoice {
        return Invoice(
            drink = this.name,
            quantity = quantity,
            unitPrice = this.unitPrice
        )
    }

}

