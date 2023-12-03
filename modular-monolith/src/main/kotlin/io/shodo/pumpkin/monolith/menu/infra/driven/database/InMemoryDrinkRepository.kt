package io.shodo.pumpkin.monolith.menu.infra.driven.database

import io.shodo.pumpkin.monolith.menu.domain.Drink
import io.shodo.pumpkin.monolith.menu.domain.DrinkRepository
import io.shodo.pumpkin.monolith.shared.domain.DrinkName
import io.shodo.pumpkin.monolith.shared.domain.Ingredient
import io.shodo.pumpkin.monolith.shared.domain.Quantity.Companion.centiliters
import io.shodo.pumpkin.monolith.shared.domain.Quantity.Companion.grams
import io.shodo.pumpkin.monolith.shared.domain.Recipe
import org.joda.money.CurrencyUnit.EUR
import org.joda.money.Money

class InMemoryDrinkRepository : DrinkRepository {

    private val items = listOf(
        Drink(
            name = DrinkName("Latte"),
            unitPrice = Money.of(EUR, 5.00),
            recipe = Recipe.from(
                Ingredient("Coffee beans") to grams(7),
                Ingredient("Milk") to centiliters(5)
            )
        ),
        Drink(
            name = DrinkName("Espresso"),
            unitPrice = Money.of(EUR, 3.00),
            recipe = Recipe.from(
                Ingredient("Coffee beans") to grams(10)
            )
        )
    ).associateBy { it.name }.toMutableMap()

    override fun findAll(): Collection<Drink> = items.values

    override fun find(name: DrinkName): Drink? = items[name]

    override fun upsert(drink: Drink) {
        items[drink.name] = drink
    }

}
