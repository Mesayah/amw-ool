group = "pl.mesayah.amw-ool"
version = "0.0.1"

plugins {
    kotlin("jvm") version "1.3.21"
}

subprojects {
    dependencies {
        implementation("com.rabbitmq:amqp-client:5.6.0")
    }
}