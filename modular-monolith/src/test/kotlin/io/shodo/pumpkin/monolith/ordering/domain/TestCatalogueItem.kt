package io.shodo.pumpkin.monolith.ordering.domain

import io.shodo.pumpkin.monolith.ordering.domain.catalogue.Catalogue
import io.shodo.pumpkin.monolith.ordering.domain.catalogue.CatalogueItem
import io.shodo.pumpkin.monolith.shared.domain.DrinkName
import io.shodo.pumpkin.monolith.shared.domain.Ingredient
import io.shodo.pumpkin.monolith.shared.domain.Quantity.Companion.centiliters
import io.shodo.pumpkin.monolith.shared.domain.Quantity.Companion.grams
import io.shodo.pumpkin.monolith.shared.domain.Recipe
import org.joda.money.CurrencyUnit
import org.joda.money.Money

enum class TestCatalogueItem(val drink: DrinkName, val unitPrice: Money, val recipe: Recipe) {

    LATTE(
        DrinkName("Latte"),
        Money.of(CurrencyUnit.EUR, 5.00),
        Recipe.from(
            Ingredient("Coffee beans") to grams(7),
            Ingredient("Milk") to centiliters(5)
        )
    );

    companion object {
        fun asCatalogue() = Catalogue { drinkName ->
            entries
                .find { it.drink == drinkName }
                ?.let { CatalogueItem(it.drink, it.unitPrice, it.recipe) }
                ?: throw UnknownDrinkException(drinkName)
        }
    }

}
