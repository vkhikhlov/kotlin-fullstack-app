private val version: String by project
private val group: String by project
val projectsVersion = version
val projectsGroup = group

allprojects {
    version = projectsVersion
    group = projectsGroup
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