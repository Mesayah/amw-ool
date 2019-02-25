group = "pl.mesayah.amw-ool"
version = "0.0.1"

dependencies {
    implementation("org.jsoup:jsoup:1.11.3")
}

plugins {
    application
}

application {
    mainClassName = "com.github.mesayah.amwool.webscraping.WikiScrapperKt"
}