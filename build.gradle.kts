import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.21"
}

repositories {
    jcenter()
    mavenCentral()
}

group = "pl.mesayah.amw-ool"
version = "0.0.1"

subprojects {
    apply(plugin = "kotlin")

    repositories {
        jcenter()
        mavenCentral()
    }

    dependencies {
        implementation(kotlin("stdlib"))
        implementation(kotlin("stdlib-jdk8"))
        
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.1.1")
        
        implementation("com.github.ajalt:clikt:1.6.0")

        testImplementation(kotlin("test"))
        testImplementation(kotlin("test-junit"))
    }

    val compileKotlin: KotlinCompile by tasks

    compileKotlin.kotlinOptions.jvmTarget = "1.8"
}