package io.shodo.pumpkin.monolith.preparation

import io.shodo.pumpkin.monolith.preparation.domain.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PreparationConfig {

    @Bean
    fun preparationQueueSupply(queue: PreparationQueue, stock: Stock): PreparationQueueSupply =
        PreparationQueueSupplier(queue)

    @Bean
    fun preparationQueueConsumption(queue: PreparationQueue, stock: Stock): PreparationQueueConsumption =
        PreparationQueueConsumer(queue, stock)

    @Bean
    fun preparationQueueMonitoring(queue: PreparationQueue): PreparationQueueMonitoring = PreparationQueueMonitor(queue)

}
