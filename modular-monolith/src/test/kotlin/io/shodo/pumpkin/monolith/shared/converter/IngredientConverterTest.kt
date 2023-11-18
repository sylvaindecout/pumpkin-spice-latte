package io.shodo.pumpkin.monolith.shared.converter

import io.shodo.pumpkin.monolith.shared.domain.Ingredient
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class IngredientConverterTest {

    private val converter = IngredientConverter()

    @Test
    fun should_convert_DB_object_to_user_object() {
        val dbObject = "Milk"

        val userObject = converter.from(dbObject)

        assertThat(userObject).isEqualTo(Ingredient("Milk"))
    }

    @Test
    fun should_convert_user_object_to_DB_object() {
        val userObject = Ingredient("Milk")

        val dbObject = converter.to(userObject)

        assertThat(dbObject).isEqualTo("Milk")
    }

}
