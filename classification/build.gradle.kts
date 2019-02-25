group = "pl.mesayah.amw-ool"
version = "0.0.1"

plugins {
    application
}

dependencies {
    compile("nz.ac.waikato.cms.weka:weka-stable:3.8.3")
}

application {
    mainClassName = "com.github.mesayah.amwool.concurrency.ClassificationKt"
}