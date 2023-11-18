package io.shodo.pumpkin.monolith.ordering.infra.driven.catalogue

import io.shodo.pumpkin.monolith.ordering.domain.catalogue.Catalogue
import io.shodo.pumpkin.monolith.ordering.domain.catalogue.CatalogueItem
import io.shodo.pumpkin.monolith.ordering.infra.driven.catalogue.client.CatalogueAccessRestClient
import io.shodo.pumpkin.monolith.ordering.infra.driven.catalogue.client.toDomain
import io.shodo.pumpkin.monolith.shared.domain.DrinkName
import org.springframework.stereotype.Component

@Component
class CatalogueAdapter(private val client: CatalogueAccessRestClient) : Catalogue {

    override fun find(drink: DrinkName): CatalogueItem? = client.find(drink)
        ?.toDomain()

}
