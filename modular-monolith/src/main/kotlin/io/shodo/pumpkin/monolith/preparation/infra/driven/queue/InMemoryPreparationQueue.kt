package io.shodo.pumpkin.monolith.preparation.infra.driven.queue

import io.shodo.pumpkin.annotations.HexagonalArchitecture
import io.shodo.pumpkin.monolith.preparation.domain.Drink
import io.shodo.pumpkin.monolith.preparation.domain.PreparationQueue
import org.springframework.stereotype.Component
import java.util.*

@Component
@HexagonalArchitecture.RightAdapter
class InMemoryPreparationQueue(
    private val queue: Queue<Drink> = LinkedList()
) : PreparationQueue {

    override val content get(): List<Drink> = queue.toList()

    override fun add(drink: Drink) {
        queue.add(drink)
    }

    override fun takeNext(): Drink? = queue.poll()

}
