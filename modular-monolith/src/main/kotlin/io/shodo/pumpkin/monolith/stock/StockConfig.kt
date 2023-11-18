package io.shodo.pumpkin.monolith.stock

import io.shodo.pumpkin.monolith.stock.domain.Stock
import io.shodo.pumpkin.monolith.stock.domain.StockAggregate
import io.shodo.pumpkin.monolith.stock.domain.StockRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class StockConfig {

    @Bean
    fun stock(repository: StockRepository): Stock = StockAggregate(repository)

}
