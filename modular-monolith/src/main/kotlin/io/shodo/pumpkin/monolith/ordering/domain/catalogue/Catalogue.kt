package io.shodo.pumpkin.monolith.ordering.domain.catalogue

import io.shodo.pumpkin.monolith.shared.domain.DrinkName

fun interface Catalogue {
    fun find(drink: DrinkName): CatalogueItem?
}
