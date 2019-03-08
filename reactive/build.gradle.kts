group = "pl.mesayah.amw-ool"
version = "0.0.1"

plugins {
    application
}

dependencies {
    compile("io.projectreactor:reactor-core:3.2.6.RELEASE")
}

application {
    mainClassName = "com.github.mesayah.amwool.reactive.BulkRenamerKt"
}