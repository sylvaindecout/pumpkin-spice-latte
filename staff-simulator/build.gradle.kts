val kotlinLoggingVersion = "3.0.5"

plugins {
  kotlin("jvm")
  kotlin("plugin.spring")
  id("org.springframework.boot")
  id("io.spring.dependency-management")
}

dependencies {
  implementation(kotlin("stdlib"))

  implementation("io.github.microutils", "kotlin-logging", kotlinLoggingVersion)

  implementation("org.springframework.boot", "spring-boot-starter-webflux")
  implementation("org.springframework.boot", "spring-boot-starter-validation")
  implementation("org.springframework.boot", "spring-boot-starter-actuator")

  testImplementation("org.springframework.boot", "spring-boot-starter-test") {
    exclude("org.junit.vintage", "junit-vintage-engine")
  }
  testImplementation("com.squareup.okhttp3", "mockwebserver")
}

springBoot {
  mainClass.set("io.shodo.pumpkin.staff.SimulationAppKt")
}
