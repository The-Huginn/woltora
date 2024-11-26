plugins {
    kotlin("jvm") version "2.0.21"
    kotlin("kapt") version "2.0.21"
    kotlin("plugin.allopen") version "2.0.21"
    kotlin("plugin.noarg") version "2.0.21"
    id("io.quarkus")
}

dependencies {
    implementation("io.quarkus:quarkus-hibernate-orm-panache-kotlin")
    implementation("io.quarkus:quarkus-jdbc-postgresql")
}

kotlin {
    jvmToolchain(21)
}