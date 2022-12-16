import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.21"
    application
}

group = "emilshteinberg"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    //implementation ("com.microsoft.sqlserver:mssql-jdbc:12.1.0")
    implementation("com.microsoft.sqlserver:mssql-jdbc:11.2.1.jre18")
    testImplementation(kotlin("test"))
    implementation("com.google.code.gson:gson:2.10")
    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:1.6.0")
    implementation("dev.inmo:tgbotapi:4.2.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}