package io.shodo.pumpkin.monolith.preparation.infra.driving.rest

import io.shodo.pumpkin.monolith.preparation.domain.Customer
import io.shodo.pumpkin.monolith.preparation.domain.Drink
import io.shodo.pumpkin.monolith.shared.domain.DrinkName
import io.shodo.pumpkin.monolith.shared.domain.Ingredient
import io.shodo.pumpkin.monolith.shared.domain.Quantity.Companion.centiliters
import io.shodo.pumpkin.monolith.shared.domain.Quantity.Companion.grams
import io.shodo.pumpkin.monolith.shared.domain.Quantity.Companion.pieces
import io.shodo.pumpkin.monolith.shared.domain.Recipe
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MappingTest {

    private val validDrinkRequestBody = DrinkRequestBody("x", mapOf("XXX" to QuantityField(1, "g")), "x")
    private val validRecipe = Recipe.from(Ingredient("XXX") to pieces(1))
    private val validDrink = Drink(DrinkName("XXX"), validRecipe, Customer("XXX"))

    @Test
    fun should_map_DrinkInPreparationResponseItem_from_domain_with_name() {
        val domainObject = validDrink.copy(name = DrinkName("LATTE"))

        val dto = domainObject.toDto()

        assertThat(dto.name).isEqualTo("LATTE")
    }

    @Test
    fun should_map_DrinkInPreparationResponseItem_from_domain_with_customer() {
        val domainObject = validDrink.copy(customer = Customer("Vincent"))

        val dto = domainObject.toDto()

        assertThat(dto.customer).isEqualTo("Vincent")
    }

    @Test
    fun should_map_DrinkRequestBody_to_domain_with_name() {
        val dto = validDrinkRequestBody.copy(name = "LATTE")

        val domainObject = dto.toDomain()

        assertThat(domainObject.name).isEqualTo(DrinkName("LATTE"))
    }

    @Test
    fun should_map_DrinkRequestBody_to_domain_with_ingredients() {
        val dto = validDrinkRequestBody.copy(
            ingredients = mapOf(
                "Coffee beans" to QuantityField(7, "g"),
                "Milk" to QuantityField(5, "cL")
            )
        )

        val domainObject = dto.toDomain()

        assertThat(domainObject.recipe).isEqualTo(
            Recipe.from(
                Ingredient("Coffee beans") to grams(7),
                Ingredient("Milk") to centiliters(5)
            )
        )
    }

    @Test
    fun should_map_DrinkRequestBody_to_domain_with_customer() {
        val dto = validDrinkRequestBody.copy(customer = "Vincent")

        val domainObject = dto.toDomain()

        assertThat(domainObject.customer).isEqualTo(Customer("Vincent"))
    }

}
