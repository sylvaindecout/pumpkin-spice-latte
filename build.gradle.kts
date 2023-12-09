import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val jvmVersion = "17"

plugins {
  base
  kotlin("jvm") version "1.9.20" apply false
  kotlin("plugin.spring") version "1.9.20" apply false
  id("org.springframework.boot") version "3.2.0" apply false
  id("io.spring.dependency-management") version "1.1.4" apply false
  id("nu.studer.jooq") version "8.2.1" apply false
}

allprojects {
  repositories {
    mavenCentral()
  }
}

subprojects {
  tasks.withType<KotlinCompile>().configureEach {
    println("Configuring JVM target for $name in project ${project.name}...")
    kotlinOptions {
      jvmTarget = jvmVersion
    }
  }
  tasks.withType<Test>().configureEach {
    println("Configuring JUnit platform for $name in project ${project.name}...")
    useJUnitPlatform()
  }
}
