package io.shodo.pumpkin.monolith.ordering.domain

import io.shodo.pumpkin.monolith.shared.domain.DrinkName
import org.assertj.core.api.Assertions.assertThat
import org.joda.money.CurrencyUnit.EUR
import org.joda.money.Money
import org.junit.jupiter.api.Test

class InvoiceTest {

    @Test
    fun should_compute_total_price() {
        val invoice = Invoice(
            drink = DrinkName("ESPRESSO"),
            quantity = 3,
            unitPrice = Money.of(EUR, 7.00),
        )

        assertThat(invoice.totalPrice)
            .isEqualTo(Money.of(EUR, 21.00))
    }

}
