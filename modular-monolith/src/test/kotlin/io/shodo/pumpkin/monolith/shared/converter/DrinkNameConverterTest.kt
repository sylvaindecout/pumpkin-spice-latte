package io.shodo.pumpkin.monolith.shared.converter

import io.shodo.pumpkin.monolith.shared.domain.DrinkName
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class DrinkNameConverterTest {

    private val converter = DrinkNameConverter()

    @Test
    fun should_convert_DB_object_to_user_object() {
        val dbObject = "Espresso"

        val userObject = converter.from(dbObject)

        assertThat(userObject).isEqualTo(DrinkName("Espresso"))
    }

    @Test
    fun should_convert_user_object_to_DB_object() {
        val userObject = DrinkName("Espresso")

        val dbObject = converter.to(userObject)

        assertThat(dbObject).isEqualTo("Espresso")
    }

}
