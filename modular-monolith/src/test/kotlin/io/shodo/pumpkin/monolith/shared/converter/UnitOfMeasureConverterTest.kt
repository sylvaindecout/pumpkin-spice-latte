package io.shodo.pumpkin.monolith.shared.converter

import io.shodo.pumpkin.monolith.shared.domain.UnitOfMeasure.MILLILITERS
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class UnitOfMeasureConverterTest {


    private val converter = UnitOfMeasureConverter()

    @Test
    fun should_convert_DB_object_to_user_object() {
        val dbObject = "mL"

        val userObject = converter.from(dbObject)

        assertThat(userObject).isEqualTo(MILLILITERS)
    }

    @Test
    fun should_convert_null_DB_object_to_user_object() {
        assertThat(converter.from(null)).isNull()
    }

    @Test
    fun should_convert_user_object_to_DB_object() {
        val userObject = MILLILITERS

        val dbObject = converter.to(userObject)

        assertThat(dbObject).isEqualTo("mL")
    }

    @Test
    fun should_convert_null_user_object_to_DB_object() {
        assertThat(converter.to(null)).isNull()
    }

}
