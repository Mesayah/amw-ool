group = "pl.mesayah.amw-ool"
version = "0.0.1"

plugins {
    application
}

dependencies {
    compile("io.projectreactor:reactor-core:3.2.6.RELEASE")
    compile("com.google.crypto.tink:tink-android:1.2.2")
}

application {
    mainClassName = "com.github.mesayah.amwool.concurrency.ReactiveKt"
}