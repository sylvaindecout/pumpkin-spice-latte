package io.shodo.pumpkin.monolith.ordering

import io.shodo.pumpkin.monolith.ordering.domain.CustomerOrderHandler
import io.shodo.pumpkin.monolith.ordering.domain.OrderingService
import io.shodo.pumpkin.monolith.ordering.domain.Stock
import io.shodo.pumpkin.monolith.ordering.domain.menu.Menu
import io.shodo.pumpkin.monolith.ordering.domain.preparation.DrinkPreparation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OrderingConfig {

    @Bean
    fun customerOrderHandlerApi(
        preparation: DrinkPreparation,
        menu: Menu,
        stock: Stock
    ): CustomerOrderHandler = OrderingService(preparation, menu, stock)

}

