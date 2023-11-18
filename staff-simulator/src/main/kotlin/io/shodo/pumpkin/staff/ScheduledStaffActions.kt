package io.shodo.pumpkin.staff

import mu.KotlinLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.function.Predicate.not

@Component
class ScheduledStaffActions(private val client: OrderProcessingRestClient) {

    private val logger = KotlinLogging.logger {}

    @Scheduled(cron = "\${application.staff-simulator.infra.preparation.cron}")
    fun processNextOrder() {
        client.processNextOrder()
            .onErrorContinue(OrderProcessingException::class::isInstance) { ex, _ -> logger.warn { "Call to Preparation API failed - ${ex.message}" } }
            .onErrorContinue(not(OrderProcessingException::class::isInstance)) { thr, _ -> logger.error(thr) { "An unexpected error occurred on call to Preparation API" } }
            .block()
    }

}
