package io.shodo.pumpkin.monolith.menu.infra.driven.database

import io.shodo.pumpkin.monolith.menu.domain.Drink
import io.shodo.pumpkin.monolith.shared.domain.DrinkName
import io.shodo.pumpkin.monolith.shared.domain.Ingredient
import io.shodo.pumpkin.monolith.shared.domain.Quantity.Companion.centiliters
import io.shodo.pumpkin.monolith.shared.domain.Quantity.Companion.grams
import io.shodo.pumpkin.monolith.shared.domain.Quantity.Companion.pieces
import io.shodo.pumpkin.monolith.shared.domain.Recipe
import org.assertj.core.api.Assertions.assertThat
import org.joda.money.CurrencyUnit.EUR
import org.joda.money.Money
import org.junit.jupiter.api.Test

class InMemoryDrinkRepositoryTest {

    private val repository = InMemoryDrinkRepository()

    @Test
    fun should_find_all_drinks() {
        val drink = repository.findAll()

        assertThat(drink).containsExactly(
            Drink(
                name = DrinkName("Latte"),
                unitPrice = Money.of(EUR, 5.00),
                recipe = Recipe.from(
                    Ingredient("Coffee beans") to grams(7),
                    Ingredient("Milk") to centiliters(5)
                )
            ), Drink(
                name = DrinkName("Espresso"),
                unitPrice = Money.of(EUR, 3.00),
                recipe = Recipe.from(
                    Ingredient("Coffee beans") to grams(10),
                )
            )
        )
    }

    @Test
    fun should_find_existing_drink() {
        val name = DrinkName("Latte")

        val drink = repository.find(name)

        assertThat(drink).isEqualTo(
            Drink(
                name = DrinkName("Latte"),
                unitPrice = Money.of(EUR, 5.00),
                recipe = Recipe.from(
                    Ingredient("Coffee beans") to grams(7),
                    Ingredient("Milk") to centiliters(5)
                )
            )
        )
    }

    @Test
    fun should_fail_to_find_unknown_drink() {
        val name = DrinkName("unknown")

        val drink = repository.find(name)

        assertThat(drink).isNull()
    }

    @Test
    fun should_update_existing_ingredient() {
        val name = DrinkName("Latte")
        val drink = Drink(
            name = name,
            unitPrice = Money.of(EUR, 5.00),
            recipe = Recipe.from(
                Ingredient("Coffee beans") to grams(7),
                Ingredient("Milk") to centiliters(5)
            )
        )

        repository.upsert(drink)

        assertThat(repository.find(name)).isEqualTo(drink)
    }

    @Test
    fun should_add_unknown_ingredient() {
        val name = DrinkName("unknown")
        val drink = Drink(
            name = name,
            unitPrice = Money.of(EUR, 5.00),
            recipe = Recipe.from(
                Ingredient("Marshmallow") to pieces(3)
            )
        )

        repository.upsert(drink)

        assertThat(repository.find(name)).isEqualTo(drink)
    }

}
