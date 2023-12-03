package io.shodo.pumpkin.rules

import com.tngtech.archunit.core.domain.JavaClass
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.ArchCondition
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.lang.ConditionEvents
import com.tngtech.archunit.lang.SimpleConditionEvent.violated
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes
import io.shodo.pumpkin.annotations.HexagonalArchitecture.LeftAdapter
import io.shodo.pumpkin.annotations.HexagonalArchitecture.Port
import io.shodo.pumpkin.annotations.HexagonalArchitecture.Port.Type.DRIVEN
import io.shodo.pumpkin.annotations.HexagonalArchitecture.Port.Type.DRIVING
import io.shodo.pumpkin.annotations.HexagonalArchitecture.RightAdapter

object HexagonalArchitectureAnnotationRules {

    @ArchTest
    val left_adapters_should_call_driving_ports: ArchRule = classes()
        .that().areAnnotatedWith(LeftAdapter::class.java)
        .should(accessDrivingPorts())
        .`as`("Left adapters should call driving ports")
        .because("If a left adapter calls no driving port, that means it is useless")

    @ArchTest
    val right_adapters_should_implement_driven_ports: ArchRule = classes()
        .that().areAnnotatedWith(RightAdapter::class.java)
        .should(implementADrivenPort())
        .`as`("Right adapters should implement driven ports")
        .because("If a right adapter does not implement a driven port, that means it is not a right adapter")

    private fun isADrivingPort(javaClass: JavaClass) = javaClass.isAnnotatedWith(Port::class.java)
            && javaClass.getAnnotationOfType(Port::class.java).value === DRIVING

    private fun isADrivenPort(javaClass: JavaClass) = javaClass.isAnnotatedWith(Port::class.java)
            && javaClass.getAnnotationOfType(Port::class.java).value === DRIVEN

    private fun accessDrivingPorts(): ArchCondition<JavaClass?> {
        return object : ArchCondition<JavaClass?>("access at least one driving port") {
            override fun check(item: JavaClass?, events: ConditionEvents?) {
                if (!item.accessAtLeastOneDrivingPort()) {
                    events?.add(violated(item, "Type $item does not implement any driven port"))
                }
            }
        }
    }

    private fun implementADrivenPort(): ArchCondition<JavaClass?> {
        return object : ArchCondition<JavaClass?>("implement a driven port") {
            override fun check(item: JavaClass?, events: ConditionEvents?) {
                if (!item.implementsAtLeastOneDrivenPort()) {
                    events?.add(violated(item, "Type $item does not implement any driven port"))
                }
            }
        }
    }

    private fun JavaClass?.accessAtLeastOneDrivingPort() = (this?.allAccessesFromSelf ?: mutableSetOf())
        .map { obj -> obj.targetOwner!! }
        .any { javaClass -> isADrivingPort(javaClass) }

    private fun JavaClass?.implementsAtLeastOneDrivenPort() = (this?.allRawInterfaces ?: mutableSetOf())
        .any { javaClass -> isADrivenPort(javaClass) }

}
