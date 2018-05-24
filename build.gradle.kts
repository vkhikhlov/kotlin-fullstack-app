allprojects {
    version = property("version")
    group = property("group")
}

buildscript {
    val kotlinVersion: String by project
    val kotlinFrontendPluginVersion: String by project
    repositories {
        mavenCentral()
        jcenter()
        maven("https://dl.bintray.com/kotlin/kotlin-eap")

    }
    dependencies {
        classpath(kotlin("gradle-plugin", kotlinVersion))
        classpath(kotlin("frontend-plugin", kotlinFrontendPluginVersion))
    }
}