package io.shodo.pumpkin.monolith.stepdefs

import io.shodo.pumpkin.monolith.ordering.domain.Customer
import io.shodo.pumpkin.monolith.shared.domain.DrinkName

class TestContext {
    var drink: DrinkName = DrinkName("UNKNOWN")
    var customer:Customer?=  null
}
