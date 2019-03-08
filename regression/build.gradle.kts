group = "pl.mesayah.amw-ool"
version = "0.0.1"

plugins {
    application
}

dependencies {
    implementation("nz.ac.waikato.cms.weka:weka-stable:3.8.3")
    implementation(project(":ml-common"))
    testImplementation(project(path = ":ml-common", configuration = "testArchives"))
}

application {
    mainClassName = "com.github.mesayah.amwool.regression.LearningKt"
}
