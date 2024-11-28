plugins {
    kotlin("jvm") version "2.0.21"
    kotlin("kapt") version "2.0.21"
    kotlin("plugin.allopen") version "2.0.21"
    kotlin("plugin.noarg") version "2.0.21"
    id("io.quarkus")
}

dependencies {
    implementation("io.quarkus:quarkus-mongodb-panache-kotlin")
    implementation("io.quarkus:quarkus-hibernate-reactive-panache-kotlin")
    implementation("io.quarkus:quarkus-reactive-pg-client")

    testImplementation("io.quarkus:quarkus-test-vertx")
    testImplementation("com.thehuginn:quarkus-sql-testing:1.0.0-SNAPSHOT")
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