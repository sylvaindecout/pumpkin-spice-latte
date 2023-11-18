package io.shodo.pumpkin.monolith.catalogue.infra.driving.rest

import io.shodo.pumpkin.monolith.TestApp
import io.shodo.pumpkin.monolith.catalogue.domain.Drink
import io.shodo.pumpkin.monolith.catalogue.domain.DrinkRepository
import io.shodo.pumpkin.monolith.shared.domain.DrinkName
import io.shodo.pumpkin.monolith.shared.domain.Ingredient
import io.shodo.pumpkin.monolith.shared.domain.Quantity.Companion.centiliters
import io.shodo.pumpkin.monolith.shared.domain.Quantity.Companion.grams
import io.shodo.pumpkin.monolith.shared.domain.Recipe
import org.joda.money.CurrencyUnit.EUR
import org.joda.money.Money
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.mockito.kotlin.given
import org.mockito.kotlin.then
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.put

@Tag("Integration")
@SpringBootTest(classes = [TestApp::class])
@AutoConfigureMockMvc
class CatalogueControllerTest {

    @MockBean
    private lateinit var drinkRepository: DrinkRepository

    @Test
    fun should_find_all_drinks(@Autowired mockMvc: MockMvc) {
        val expectedResponseBody = """[{
            "name":"LATTE",
            "unitPrice": {
                "amountMinor":500,
                "currencyUnit":"EUR",
                "scale":2
            },
            "ingredients": {
                "Coffee beans": {
                    "amount":7000,
                    "unitOfMeasure":"mg"
                },
                "Milk": {
                    "amount":50,
                    "unitOfMeasure":"mL"
                }
            }
        }]""".trimIndent()
        given(drinkRepository.findAll()).willReturn(
            listOf(
                Drink(
                    DrinkName("LATTE"), Money.of(EUR, 5.00), Recipe.from(
                        Ingredient("Coffee beans") to grams(7),
                        Ingredient("Milk") to centiliters(5)
                    )
                )
            )
        )

        mockMvc.get("/catalogue/drinks/").andExpect {
            status { isOk() }
            content { json(expectedResponseBody) }
        }
    }

    @Test
    fun should_find_drink(@Autowired mockMvc: MockMvc) {
        val expectedResponseBody = """{
            "name":"LATTE",
            "unitPrice": {
                "amountMinor":500,
                "currencyUnit":"EUR",
                "scale":2
            },
            "ingredients": {
                "Coffee beans": {
                    "amount":7000,
                    "unitOfMeasure":"mg"
                },
                "Milk": {
                    "amount":50,
                    "unitOfMeasure":"mL"
                }
            }
        }""".trimIndent()
        given(drinkRepository.find(DrinkName("LATTE")))
            .willReturn(
                Drink(
                    DrinkName("LATTE"), Money.of(EUR, 5.00), Recipe.from(
                        Ingredient("Coffee beans") to grams(7),
                        Ingredient("Milk") to centiliters(5)
                    )
                )
            )

        mockMvc.get("/catalogue/drinks/{name}", "LATTE").andExpect {
            status { isOk() }
            content { json(expectedResponseBody) }
        }
    }

    @Test
    fun should_upsert_drink(@Autowired mockMvc: MockMvc) {
        val requestBody = """{
            "unitPrice": {
                "amountMinor":500,
                "currencyUnit":"EUR",
                "scale":2
            },
            "ingredients": {
                "Coffee beans": {
                    "amount":7000,
                    "unitOfMeasure":"mg"
                },
                "Milk": {
                    "amount":50,
                    "unitOfMeasure":"mL"
                }
            }
        }""".trimIndent()
        mockMvc.put("/catalogue/drinks/{name}", "LATTE") {
            contentType = APPLICATION_JSON
            content = requestBody
        }.andExpect {
            status { isNoContent() }
        }

        then(drinkRepository).should().upsert(
            Drink(
                DrinkName("LATTE"), Money.of(EUR, 5.00), Recipe.from(
                    Ingredient("Coffee beans") to grams(7),
                    Ingredient("Milk") to centiliters(5)
                )
            )
        )
    }

}
