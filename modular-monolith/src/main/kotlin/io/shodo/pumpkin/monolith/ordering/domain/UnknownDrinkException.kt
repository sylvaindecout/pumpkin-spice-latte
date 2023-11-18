package io.shodo.pumpkin.monolith.ordering.domain

import io.shodo.pumpkin.monolith.shared.domain.DrinkName

class UnknownDrinkException(drink: DrinkName) : RuntimeException("No drink exists with name $drink")
