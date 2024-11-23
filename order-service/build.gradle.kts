plugins {
    kotlin("jvm") version "2.0.21"
    kotlin("plugin.allopen") version "2.0.21"
    kotlin("plugin.noarg") version "2.0.21"
    id("io.quarkus")
}

dependencies {
    implementation("io.quarkus:quarkus-mongodb-panache-kotlin")
    implementation("io.quarkus:quarkus-hibernate-orm-panache-kotlin")
    implementation("io.quarkus:quarkus-jdbc-postgresql")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
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
