plugins {
    kotlin("jvm")
    kotlin("plugin.allopen") version "2.0.21"
    id("io.quarkus")
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation("com.thehuginn:influx-db-dev-service:1.0.0-SNAPSHOT")
    implementation("io.quarkus:quarkus-scheduler")
    implementation("io.quarkus:quarkus-rest-client")
    implementation("io.quarkus:quarkus-rest-client-jackson")
}

kotlin {
    jvmToolchain(21)
}

//java {
//    sourceCompatibility = JavaVersion.VERSION_21
//    targetCompatibility = JavaVersion.VERSION_21
//}
//
//tasks.withType<Test> {
//    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
//}
//allOpen {
//    annotation("jakarta.ws.rs.Path")
//    annotation("jakarta.enterprise.context.ApplicationScoped")
//    annotation("jakarta.persistence.Entity")
//    annotation("io.quarkus.test.junit.QuarkusTest")
//}
//
//tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
//    kotlinOptions.jvmTarget = JavaVersion.VERSION_21.toString()
//    kotlinOptions.javaParameters = true
//}
