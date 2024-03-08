package io.shodo.pumpkin.monolith.stepdefs;

import io.cucumber.java.PendingException
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.shodo.pumpkin.monolith.ordering.domain.*
import io.shodo.pumpkin.monolith.ordering.domain.preparation.Drink
import io.shodo.pumpkin.monolith.shared.domain.DrinkName
import org.assertj.core.api.SoftAssertions


class OrderStepdefs(private val testContext: TestContext) {


    private lateinit var call:() -> Unit

    private val drinksSentToPreparation: MutableList<Drink> = mutableListOf<Drink>()
    private fun allIngredientsAreInStock() = Stock { _, _ -> true }
    private fun allIngredientsAreOutOfStock() = Stock { _, _ -> false }

    private var service: OrderingService

    @When("he order, an hot coca-cola, drink not present on the menu$")
    fun `he order, an hot coca-cola, drink not present on the menu`() {
        testContext.drink = DrinkName("UNKNOWN")
        call = { service.process(Order(testContext.drink, 1, testContext.customer!!)) }
    }

    @Given("a client")
    fun a_client() {
        testContext.customer = Customer("Vincent");
    }

    @Given("latte ingredients are missing")
    fun latte_ingredients_are_missing() {
        service = OrderingService(
            preparation = { drinksSentToPreparation += it },
            menu = TestMenuItem.asMenu(),
            stock = allIngredientsAreOutOfStock()
        )
    }

    @When("he order a latte drink")
    fun he_order_a_latte_drink() {
        call = { service.process(Order(TestMenuItem.LATTE.drink, 1, Customer("Vincent"))) }
    }

    @Then("client is notify that the drink  is currently unavailable")
    fun client_is_notify_that_the_drink_is_currently_unavailable() {
        SoftAssertions.assertSoftly {
            it.assertThatExceptionOfType(UnavailableIngredientException::class.java)
                .isThrownBy(call)
                .withMessage("Ingredient ${TestMenuItem.LATTE.recipe.asMap().keys.first()} is currently unavailable")
            it.assertThat(drinksSentToPreparation).isEmpty()
        }
    }
    @Then("^client is notify that the drink hot coca-cola doesn't exist$")
    fun `client is notify that the drink hot coca-cola doesn't exist`() {
        SoftAssertions.assertSoftly {
            it.assertThatExceptionOfType(UnknownDrinkException::class.java)
                .isThrownBy(call)
                .withMessage("No drink exists with name ${testContext.drink}")
            it.assertThat(drinksSentToPreparation).isEmpty()
        }
    }

    @Then("client is received an invoice with the price and the quantity")
    fun client_is_received_an_invoice_with_the_price_and_the_quantity() {
        SoftAssertions.assertSoftly {
            it.assertThat(call)
                .isEqualTo(Invoice(TestMenuItem.LATTE.drink, 1, TestMenuItem.LATTE.unitPrice))
        }
    }

    @Then("the drink is sent to the preparation")
    fun the_drink_is_sent_to_the_preparation() {
        SoftAssertions.assertSoftly {
            it.assertThat(drinksSentToPreparation).containsExactly(
                Drink(TestMenuItem.LATTE.drink, TestMenuItem.LATTE.recipe, testContext.customer!!),
                Drink(TestMenuItem.LATTE.drink, TestMenuItem.LATTE.recipe, testContext.customer!!),
                Drink(TestMenuItem.LATTE.drink, TestMenuItem.LATTE.recipe, testContext.customer!!)
            )
        }
    }

    init {
        this.service = OrderingService(
            preparation = { drinksSentToPreparation += it },
            menu = TestMenuItem.asMenu(),
            stock = allIngredientsAreInStock()
        )
    }
}
