group = "pl.mesayah.amw-ool"
version = "0.0.1"

plugins {
    application
}

dependencies {
    compile("com.github.kittinunf.fuel:fuel:1.15.0")
    compile("com.github.kittinunf.fuel:fuel-jackson:1.15.0")
    compile("com.github.kittinunf.fuel:fuel-coroutines:1.15.0")
}

application {
    mainClassName = "com.github.mesayah.amwool.concurrency.ConcurrencyKt"
}