package io.shodo.pumpkin.monolith.ordering.domain.menu

import io.shodo.pumpkin.monolith.shared.domain.DrinkName

fun interface Menu {
    fun find(drink: DrinkName): MenuItem?
}
