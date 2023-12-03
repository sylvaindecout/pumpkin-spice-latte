package io.shodo.pumpkin.monolith.ordering.domain

import io.shodo.pumpkin.monolith.ordering.domain.TestMenuItem.LATTE
import io.shodo.pumpkin.monolith.ordering.domain.preparation.Drink
import io.shodo.pumpkin.monolith.shared.domain.DrinkName
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.jupiter.api.Test

class OrderingServiceTest {

    private val drinksSentToPreparation = mutableListOf<Drink>()

    @Test
    fun should_process_order() {
        val service = OrderingService(
            preparation = { drinksSentToPreparation += it },
            menu = TestMenuItem.asMenu(),
            stock = allIngredientsAreInStock()
        )
        val customer = Customer("Vincent")
        val orderedQuantity = 1

        val invoice = service.process(Order(LATTE.drink, orderedQuantity, customer))

        assertSoftly {
            it.assertThat(invoice).isEqualTo(Invoice(LATTE.drink, orderedQuantity, LATTE.unitPrice))
            it.assertThat(drinksSentToPreparation).containsExactly(
                Drink(LATTE.drink, LATTE.recipe, customer)
            )
        }
    }

    @Test
    fun should_process_order_with_more_than_1_drink() {
        val service = OrderingService(
            preparation = { drinksSentToPreparation += it },
            menu = TestMenuItem.asMenu(),
            stock = allIngredientsAreInStock()
        )
        val customer = Customer("Vincent")
        val orderedQuantity = 3

        val invoice = service.process(Order(LATTE.drink, orderedQuantity, customer))

        assertSoftly {
            it.assertThat(invoice).isEqualTo(Invoice(LATTE.drink, orderedQuantity, LATTE.unitPrice))
            it.assertThat(drinksSentToPreparation).containsExactly(
                Drink(LATTE.drink, LATTE.recipe, customer),
                Drink(LATTE.drink, LATTE.recipe, customer),
                Drink(LATTE.drink, LATTE.recipe, customer)
            )
        }
    }

    @Test
    fun should_fail_to_process_order_if_ingredients_are_missing() {
        val service = OrderingService(
            preparation = { drinksSentToPreparation += it },
            menu = TestMenuItem.asMenu(),
            stock = allIngredientsAreOutOfStock()
        )

        val call: () -> Unit = { service.process(Order(LATTE.drink, 1, Customer("Vincent"))) }

        assertSoftly {
            it.assertThatExceptionOfType(UnavailableIngredientException::class.java)
                .isThrownBy(call)
                .withMessage("Ingredient ${LATTE.recipe.asMap().keys.first()} is currently unavailable")
            it.assertThat(drinksSentToPreparation).isEmpty()
        }
    }

    @Test
    fun should_fail_to_process_order_if_drink_is_not_in_menu() {
        val service = OrderingService(
            preparation = { drinksSentToPreparation += it },
            menu = TestMenuItem.asMenu(),
            stock = allIngredientsAreInStock()
        )
        val drink = DrinkName("UNKNOWN")

        val call: () -> Unit = { service.process(Order(drink, 1, Customer("Vincent"))) }

        assertSoftly {
            it.assertThatExceptionOfType(UnknownDrinkException::class.java)
                .isThrownBy(call)
                .withMessage("No drink exists with name $drink")
            it.assertThat(drinksSentToPreparation).isEmpty()
        }
    }

    private fun allIngredientsAreInStock() = Stock { _, _ -> true }
    private fun allIngredientsAreOutOfStock() = Stock { _, _ -> false }

}
