package io.shodo.pumpkin.monolith.stepdefs;

import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.shodo.pumpkin.monolith.ordering.domain.Customer


class OrderStepdefs(private val testContext: TestContext) {

    /*
    constructor(testContext: TestContext) {
        this.testContext = testContext
        this.drinksSentToPreparation = mutableListOf<Drink>()
        this.service = OrderingService(
            preparation = { drinksSentToPreparation += it },
            menu = TestMenuItem.asMenu(),
            stock = allIngredientsAreInStock()
        )
    }

    private val drinksSentToPreparation: MutableList<Drink>
    private fun allIngredientsAreInStock() = Stock { _, _ -> true }

    private val service: OrderingService

/

 */
    @Then("^client is notify that the drink hot coca-cola doesn't exist$")
    fun `client is notify that the drink hot coca-cola doesn't exist`() {

    }
    @When("he order, an hot coca-cola, drink not present on the menu$")
    fun `he order, an hot coca-cola, drink not present on the menu`() {
        //val drink = DrinkName("UNKNOWN")
        //val call: () -> Unit = { service.process(Order(drink, 1, testContext.customer!!)) }
    }
    @Given("a client")
    fun a_client() {
        // Write code here that turns the phrase above into concrete actions
        testContext.customer= Customer("Vincent");
    }
}
