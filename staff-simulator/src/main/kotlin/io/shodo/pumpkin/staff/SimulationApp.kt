package io.shodo.pumpkin.staff

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication(
    scanBasePackageClasses = [
        StaffSimulatorConfig::class
    ]
)
class SimulationApp

fun main(args: Array<String>) {
    runApplication<SimulationApp>(*args)
}
