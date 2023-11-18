package io.shodo.pumpkin.monolith

import io.shodo.pumpkin.monolith.catalogue.CatalogueConfig
import io.shodo.pumpkin.monolith.ordering.OrderingConfig
import io.shodo.pumpkin.monolith.preparation.PreparationConfig
import io.shodo.pumpkin.monolith.stock.StockConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackageClasses = [
        AppConfig::class,
        CatalogueConfig::class,
        OrderingConfig::class,
        PreparationConfig::class,
        StockConfig::class
    ]
)
class App

fun main(args: Array<String>) {
    runApplication<App>(*args)
}
