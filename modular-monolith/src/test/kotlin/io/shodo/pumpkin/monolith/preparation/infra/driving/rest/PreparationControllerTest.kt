package io.shodo.pumpkin.monolith.preparation.infra.driving.rest

import io.shodo.pumpkin.monolith.TestApp
import io.shodo.pumpkin.monolith.preparation.domain.*
import io.shodo.pumpkin.monolith.shared.domain.DrinkName
import io.shodo.pumpkin.monolith.shared.domain.Ingredient
import io.shodo.pumpkin.monolith.shared.domain.Quantity.Companion.centiliters
import io.shodo.pumpkin.monolith.shared.domain.Quantity.Companion.grams
import io.shodo.pumpkin.monolith.shared.domain.Recipe
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.mockito.kotlin.given
import org.mockito.kotlin.then
import org.mockito.kotlin.times
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@Tag("Integration")
@SpringBootTest(classes = [TestApp::class])
@AutoConfigureMockMvc
class PreparationControllerTest {

    @MockBean
    private lateinit var supplier: PreparationQueueSupply

    @MockBean
    private lateinit var consumer: PreparationQueueConsumption

    @MockBean
    private lateinit var monitor: PreparationQueueMonitoring

    @Test
    fun should_queue_drink(@Autowired mockMvc: MockMvc) {
        val requestBody = """{
            "name": "LATTE",
            "ingredients": {
                "Coffee beans": {
                    "amount":7,
                    "unitOfMeasure":"g"
                },
                "Milk": {
                    "amount":5,
                    "unitOfMeasure":"cL"
                }
            },
            "customer": "Vincent"
        }""".trimIndent()

        mockMvc.post("/preparation/drinks") {
            contentType = APPLICATION_JSON
            content = requestBody
        }.andExpect {
            status { isCreated() }
        }

        then(supplier).should(times(1)).add(
            Drink(
                name = DrinkName("LATTE"),
                recipe = Recipe.from(
                    Ingredient("Coffee beans") to grams(7),
                    Ingredient("Milk") to centiliters(5)
                ),
                customer = Customer("Vincent")
            )
        )
    }

    @Test
    fun should_process_next_drink(@Autowired mockMvc: MockMvc) {
        mockMvc.post("/preparation/drinks/take").andExpect {
            status { isNoContent() }
        }

        then(consumer).should(times(1)).processNext()
    }

    @Test
    fun should_get_content(@Autowired mockMvc: MockMvc) {
        given(monitor.content).willReturn(
            listOf(
                Drink(
                    name = DrinkName("LATTE"),
                    recipe = Recipe.from(
                        Ingredient("Coffee beans") to grams(7),
                        Ingredient("Milk") to centiliters(5)
                    ),
                    customer = Customer("Vincent")
                )
            )
        )

        mockMvc.get("/preparation/drinks").andExpect {
            status { isOk() }
            content {
                json(
                    """[{
                    "name": "LATTE",
                    "customer": "Vincent"
                }]""".trimIndent()
                )
            }
        }
    }

}
