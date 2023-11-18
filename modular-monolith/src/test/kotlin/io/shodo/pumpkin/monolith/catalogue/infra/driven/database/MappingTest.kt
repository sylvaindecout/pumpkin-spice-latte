package io.shodo.pumpkin.monolith.catalogue.infra.driven.database

import io.shodo.pumpkin.monolith.shared.domain.Ingredient
import io.shodo.pumpkin.monolith.shared.domain.Quantity.Companion.centiliters
import io.shodo.pumpkin.monolith.shared.domain.Quantity.Companion.grams
import io.shodo.pumpkin.monolith.shared.domain.Quantity.Companion.pieces
import io.shodo.pumpkin.monolith.shared.domain.Recipe
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class MappingTest {

    @Test
    fun should_map_recipe_to_domain() {
        val dto = mapOf(
            "Coffee beans" to QuantityField(7, "g"),
            "Milk" to QuantityField(50, "mL"),
            "Marshmallow" to QuantityField(1, null)
        )

        val domainObject = dto.toDomain()

        assertThat(domainObject).isEqualTo(
            Recipe.from(
                Ingredient("Coffee beans") to grams(7),
                Ingredient("Milk") to centiliters(5),
                Ingredient("Marshmallow") to pieces(1)
            )
        )
    }

    @Test
    fun should_map_recipe_from_domain() {
        val domainObject = Recipe.from(
            Ingredient("Coffee beans") to grams(7),
            Ingredient("Milk") to centiliters(5),
            Ingredient("Marshmallow") to pieces(1)
        )

        val dto = domainObject.toRecord()

        assertThat(dto).isEqualTo(
            mapOf(
                "Coffee beans" to QuantityField(7000, "mg"),
                "Milk" to QuantityField(50, "mL"),
                "Marshmallow" to QuantityField(1, null)
            )
        )
    }

}
