package io.shodo.pumpkin.monolith.catalogue

import io.shodo.pumpkin.monolith.catalogue.domain.Catalogue
import io.shodo.pumpkin.monolith.catalogue.domain.CatalogueAggregate
import io.shodo.pumpkin.monolith.catalogue.domain.DrinkRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CatalogueConfig {

    @Bean
    fun catalogue(repository: DrinkRepository): Catalogue = CatalogueAggregate(repository)

}
