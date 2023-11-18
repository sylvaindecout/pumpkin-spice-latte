package io.shodo.pumpkin.monolith.ordering.infra.driving.rest

import io.shodo.pumpkin.monolith.ordering.domain.Customer
import io.shodo.pumpkin.monolith.ordering.domain.Invoice
import io.shodo.pumpkin.monolith.shared.domain.DrinkName
import org.assertj.core.api.Assertions.assertThat
import org.joda.money.CurrencyUnit.EUR
import org.joda.money.Money
import org.junit.jupiter.api.Test

class MappingTest {

    private val validOrderRequestBody = OrderRequestBody("x", 1, "x")
    private val validInvoice = Invoice(DrinkName("x"), 1, Money.zero(EUR))

    @Test
    fun should_map_OrderRequestBody_to_domain_with_drink() {
        val dto = validOrderRequestBody.copy(drink = "LATTE")

        val domainObject = dto.toDomain()

        assertThat(domainObject.drink).isEqualTo(DrinkName("LATTE"))
    }

    @Test
    fun should_map_OrderRequestBody_to_domain_with_quantity() {
        val dto = validOrderRequestBody.copy(quantity = 50)

        val domainObject = dto.toDomain()

        assertThat(domainObject.quantity).isEqualTo(50)
    }

    @Test
    fun should_map_OrderRequestBody_to_domain_with_customer() {
        val dto = validOrderRequestBody.copy(customer = "Vincent")

        val domainObject = dto.toDomain()

        assertThat(domainObject.customer).isEqualTo(Customer("Vincent"))
    }

    @Test
    fun should_map_Invoice_to_DTO_with_total_price() {
        val domainObject = validInvoice.copy(
            quantity = 50,
            unitPrice = Money.of(EUR, 50.99)
        )

        val dto = domainObject.toDto()

        assertThat(dto.totalPrice).isEqualTo(
            MoneyField(
                amountMinor = 2549_50,
                currencyUnit = "EUR",
                scale = 2
            )
        )
    }

}
