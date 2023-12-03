package io.shodo.pumpkin.monolith.menu

import io.shodo.pumpkin.monolith.menu.domain.DrinkRepository
import io.shodo.pumpkin.monolith.menu.domain.Menu
import io.shodo.pumpkin.monolith.menu.domain.MenuAggregate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MenuConfig {

    @Bean
    fun menu(repository: DrinkRepository): Menu = MenuAggregate(repository)

}
