package io.shodo.pumpkin.monolith.stock.infra.driving.rest

import io.shodo.pumpkin.monolith.TestApp
import io.shodo.pumpkin.monolith.shared.domain.Ingredient
import io.shodo.pumpkin.monolith.shared.domain.Quantity.Companion.centiliters
import io.shodo.pumpkin.monolith.shared.domain.Quantity.Companion.milliliters
import io.shodo.pumpkin.monolith.stock.domain.StockItem
import io.shodo.pumpkin.monolith.stock.domain.StockRepository
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
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put

@Tag("Integration")
@SpringBootTest(classes = [TestApp::class])
@AutoConfigureMockMvc
internal class StockControllerTest {

    @MockBean
    private lateinit var stockRepository: StockRepository

    @Test
    fun should_find_stock_item(@Autowired mockMvc: MockMvc) {
        val expectedResponseBody = """{
            "ingredient":"Milk",
            "currentQuantity": {
                "amount":50,
                "unitOfMeasure":"mL"
            }
        }""".trimIndent()
        given(stockRepository.find(Ingredient("Milk")))
            .willReturn(StockItem(Ingredient("Milk"), centiliters(5)))

        mockMvc.get("/stock/ingredients/{name}", "Milk").andExpect {
            status { isOk() }
            content { json(expectedResponseBody) }
        }
    }

    @Test
    fun should_use_stock_item(@Autowired mockMvc: MockMvc) {
        given(stockRepository.find(Ingredient("Milk")))
            .willReturn(StockItem(Ingredient("Milk"), centiliters(15)))
        val requestBody = """{
            "ingredient": "Milk",
            "usedQuantity": {
                "amount":50,
                "unitOfMeasure":"mL"
            }
        }""".trimIndent()
        mockMvc.post("/stock/use") {
            contentType = APPLICATION_JSON
            content = requestBody
        }.andExpect {
            status { isNoContent() }
        }

        then(stockRepository).should().upsert(StockItem(Ingredient("Milk"), centiliters(10)))
    }

    @Test
    fun should_upsert_stock_item(@Autowired mockMvc: MockMvc) {
        val requestBody = """{
            "currentQuantity": {
                "amount":50,
                "unitOfMeasure":"mL"
            }
        }""".trimIndent()
        mockMvc.put("/stock/ingredients/{name}", "Milk") {
            contentType = APPLICATION_JSON
            content = requestBody
        }.andExpect {
            status { isNoContent() }
        }

        then(stockRepository).should().upsert(StockItem(Ingredient("Milk"), milliliters(50)))
    }

}
