package io.shodo.pumpkin.monolith.ordering.domain

import io.shodo.pumpkin.monolith.ordering.domain.catalogue.Catalogue
import io.shodo.pumpkin.monolith.ordering.domain.catalogue.CatalogueItem
import io.shodo.pumpkin.monolith.ordering.domain.preparation.Drink
import io.shodo.pumpkin.monolith.ordering.domain.preparation.DrinkPreparation
import io.shodo.pumpkin.monolith.shared.domain.DrinkName

class OrderingService(
    val preparation: DrinkPreparation,
    val catalogue: Catalogue,
    val stock: Stock
) : CustomerOrderHandler {

    override fun process(order: Order): Invoice = findInCatalogue(order.drink)
        .also { failOnUnavailableIngredient(it, order.quantity) }
        .also { sendToPreparation(it, order) }
        .toInvoiceWith(order.quantity)

    private fun findInCatalogue(drink: DrinkName) = catalogue.find(drink) ?: throw UnknownDrinkException(drink)

    private fun sendToPreparation(catalogueItem: CatalogueItem, order: Order) = catalogueItem
        .asDrinkFor(order.customer)
        .let { drink -> repeat(order.quantity) { preparation.prepare(drink) } }

    private fun failOnUnavailableIngredient(catalogueItem: CatalogueItem, orderedQuantity: Int) {
        catalogueItem.recipe
            .asMap().toList()
            .map { it.first to it.second * orderedQuantity }
            .find { !stock.hasEnoughOf(it.first, it.second) }
            ?.let { throw UnavailableIngredientException(it.first) }
    }

    private fun CatalogueItem.asDrinkFor(customer: Customer) = Drink(
        name = this.name,
        recipe = this.recipe,
        customer = customer
    )

    private fun CatalogueItem.toInvoiceWith(quantity: Int): Invoice {
        return Invoice(
            drink = this.name,
            quantity = quantity,
            unitPrice = this.unitPrice
        )
    }

}

