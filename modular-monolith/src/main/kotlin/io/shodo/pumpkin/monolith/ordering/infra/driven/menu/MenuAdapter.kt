package io.shodo.pumpkin.monolith.ordering.infra.driven.menu

import io.shodo.pumpkin.monolith.ordering.domain.menu.Menu
import io.shodo.pumpkin.monolith.ordering.domain.menu.MenuItem
import io.shodo.pumpkin.monolith.ordering.infra.driven.menu.client.MenuAccessRestClient
import io.shodo.pumpkin.monolith.ordering.infra.driven.menu.client.toDomain
import io.shodo.pumpkin.monolith.shared.domain.DrinkName
import org.springframework.stereotype.Component

@Component
class MenuAdapter(private val client: MenuAccessRestClient) : Menu {

    override fun find(drink: DrinkName): MenuItem? = client.find(drink)
        ?.toDomain()

}
