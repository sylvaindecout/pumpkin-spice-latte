package io.shodo.pumpkin.monolith.shared.converter

import org.assertj.core.api.Assertions.assertThat
import org.joda.money.CurrencyUnit
import org.junit.jupiter.api.Test

internal class CurrencyUnitConverterTest {

    private val converter = CurrencyUnitConverter()

    @Test
    fun should_convert_DB_object_to_user_object() {
        val dbObject = "EUR"

        val userObject = converter.from(dbObject)

        assertThat(userObject).isEqualTo(CurrencyUnit.EUR)
    }

    @Test
    fun should_convert_user_object_to_DB_object() {
        val userObject = CurrencyUnit.EUR

        val dbObject = converter.to(userObject)

        assertThat(dbObject).isEqualTo("EUR")
    }

}
