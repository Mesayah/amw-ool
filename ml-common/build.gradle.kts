group = "pl.mesayah.amw-ool"
version = "0.0.1"

plugins {
    id("com.github.hauner.jarTest") version "1.0"
}

apply(plugin = "com.github.hauner.jarTest")

dependencies {
    compile("nz.ac.waikato.cms.weka:weka-stable:3.8.3")
}