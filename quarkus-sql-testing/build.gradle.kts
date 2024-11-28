plugins {
    kotlin("jvm") version "2.0.21"
    kotlin("plugin.allopen") version "2.0.21"
    id("maven-publish")
}

repositories {
    mavenCentral()
    mavenLocal()
}


dependencies {
    implementation("io.quarkus:quarkus-arc:3.16.1")
    implementation("io.quarkus:quarkus-junit5:3.16.1")
    implementation("io.quarkus:quarkus-reactive-pg-client:3.16.1")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifactId = "quarkus-sql-testing"
            groupId = "com.thehuginn"
            version = "1.0.0-SNAPSHOT"
        }
    }
}