package io.shodo.pumpkin.monolith.stock.infra.driven.database

import io.shodo.pumpkin.monolith.TestApp
import io.shodo.pumpkin.monolith.shared.domain.Ingredient
import io.shodo.pumpkin.monolith.shared.domain.Quantity
import io.shodo.pumpkin.monolith.stock.domain.StockItem
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jooq.AutoConfigureJooq
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD
import org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD
import org.testcontainers.containers.PostgreSQLContainer

@Tag("Integration")
@AutoConfigureJooq
@SpringBootTest(classes = [TestApp::class])
@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = ["/data/init_stock.sql"])
@Sql(executionPhase = AFTER_TEST_METHOD, scripts = ["/data/clean_stock.sql"])
class JdbcStockRepositoryTest {

    companion object {
        private val DB by lazy { PostgreSQLContainer("postgres:15.3") }

        @JvmStatic
        @DynamicPropertySource
        fun registerDbProperties(registry: DynamicPropertyRegistry) {
            DB.start()
            registry.apply {
                with(DB) {
                    add("spring.datasource.url") { jdbcUrl }
                    add("spring.datasource.username") { username }
                    add("spring.datasource.password") { password }
                }
            }
        }
    }

    @Autowired
    private lateinit var repository: JdbcStockRepository

    @Test
    fun should_find_existing_ingredient() {
        val ingredient = Ingredient("Milk")

        val stockItem = repository.find(ingredient)

        assertThat(stockItem).isEqualTo(
            StockItem(
                ingredient = ingredient,
                currentQuantity = Quantity.centiliters(1_000)
            )
        )
    }

    @Test
    fun should_fail_to_find_unknown_ingredient() {
        val ingredient = Ingredient("unknown")

        val stockItem = repository.find(ingredient)

        assertThat(stockItem).isNull()
    }

    @Test
    fun should_update_existing_ingredient() {
        val ingredient = Ingredient("Milk")
        val stockItem = StockItem(ingredient, Quantity.centiliters(97))

        repository.upsert(stockItem)

        assertThat(repository.find(ingredient)).isEqualTo(stockItem)
    }

    @Test
    fun should_add_unknown_ingredient() {
        val ingredient = Ingredient("unknown")
        val stockItem = StockItem(ingredient, Quantity.centiliters(97))

        repository.upsert(stockItem)

        assertThat(repository.find(ingredient)).isEqualTo(stockItem)
    }

    @Test
    fun should_delete_existing_ingredient() {
        val ingredient = Ingredient("Milk")

        repository.delete(ingredient)

        assertThat(repository.find(ingredient)).isNull()
    }

    @Test
    fun should_delete_unknown_ingredient() {
        val ingredient = Ingredient("unknown")

        repository.delete(ingredient)

        assertThat(repository.find(ingredient)).isNull()
    }

}
