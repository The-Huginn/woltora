plugins {
    kotlin("jvm") version "2.0.21"
    kotlin("kapt") version "2.0.21"
    kotlin("plugin.allopen") version "2.0.21"
    kotlin("plugin.noarg") version "2.0.21"
    id("io.quarkus")
}

val mapstructVersion: String by project

dependencies {
    implementation("io.quarkus:quarkus-mongodb-panache-kotlin")
    implementation("io.quarkus:quarkus-hibernate-orm-panache-kotlin")
    implementation("io.quarkus:quarkus-jdbc-postgresql")
//    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation("com.thehuginn:quarkus-sql-testing:1.0.0-SNAPSHOT")
    implementation(kotlin("stdlib-jdk8"))
}

allOpen {
    annotation("io.quarkus.mongodb.panache.common.MongoEntity")
}

noArg {
    annotation("io.quarkus.mongodb.panache.common.MongoEntity")
    invokeInitializers = true
}

kotlin {
    jvmToolchain(21)
}

//quarkusDev {
//    doFirst {
//        System.setProperty("debug", "5005")
//    }
//}
//tasks.named('quarkusDev') {
//    doFirst {
//        System.setProperty("debug", "5005")
//    }
//}
repositories {
    mavenCentral()
}