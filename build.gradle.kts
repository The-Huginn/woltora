import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.internal.KaptWithoutKotlincTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.0.21"
    kotlin("plugin.allopen") version "2.0.21"
    kotlin("kapt") version "2.0.21"
    id("io.quarkus")
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project
val mapstructVersion: String by project

repositories {
    gradlePluginPortal()
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.allopen")
    apply(plugin = "org.jetbrains.kotlin.kapt")
    apply(plugin = "io.quarkus")

    repositories {
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
        implementation("io.quarkus:quarkus-arc")
        implementation("io.quarkus:quarkus-rest")
        implementation("io.quarkus:quarkus-kotlin")
        implementation("io.quarkus:quarkus-rest-jackson")
        implementation("io.quarkus:quarkus-messaging-kafka")
        implementation("io.quarkus:quarkus-config-yaml")

        implementation("org.jetbrains.kotlin:kotlin-stdlib")

        implementation("org.mapstruct:mapstruct:${mapstructVersion}")
        kapt("org.mapstruct:mapstruct-processor:${mapstructVersion}")

        testImplementation("io.quarkus:quarkus-junit5")
        testImplementation("io.rest-assured:rest-assured")
        testImplementation("org.awaitility:awaitility")
        testImplementation("org.assertj:assertj-core:3.26.3")
        testImplementation("com.thehuginn:quarkus-sql-testing:1.0.0-SNAPSHOT")
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    tasks.withType<Test> {
        systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
    }

    allOpen {
        annotation("jakarta.ws.rs.Path")
        annotation("jakarta.enterprise.context.ApplicationScoped")
        annotation("jakarta.persistence.Entity")
        annotation("io.quarkus.test.junit.QuarkusTest")
    }

    tasks.withType<KotlinCompile> {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_21
            javaParameters = true
        }
    }

    tasks.withType<KaptWithoutKotlincTask>()
        .configureEach {
            kaptProcessJvmArgs.add("-Xmx512m")
        }

    kapt {
        correctErrorTypes = true
        arguments {
            arg("mapstruct.defaultComponentModel", "jakarta-cdi")
            arg("mapstruct.defaultInjectionStrategy", "constructor")
            arg("mapstruct.unmappedTargetPolicy", "ERROR")
        }
    }
}

group "com.thehuginn"
version "1.0.0-SNAPSHOT"
