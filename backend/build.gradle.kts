import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion: String by project
val ktorVersion: String by project
val exposedVersion: String by project
val compileKotlin: KotlinCompile by tasks
kotlin.experimental.coroutines = Coroutines.ENABLE
compileKotlin.kotlinOptions.jvmTarget = "1.8"

plugins {
    id("java")
    id("kotlin")
    id("application")
}

setProperty("mainClassName", "MainKt")

repositories {
    jcenter()
    maven("https://dl.bintray.com/kotlin/ktor")
    maven("https://dl.bintray.com/kotlin/exposed")
}

dependencies {
    compile(kotlin("stdlib-jdk8", kotlinVersion))
    compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    compile("io.ktor:ktor-server-netty:$ktorVersion")
    compile("io.ktor:ktor-gson:$ktorVersion")
    compile("io.ktor:ktor-locations:$ktorVersion")
    compile("org.jetbrains.exposed:exposed:$exposedVersion")
    compile("com.h2database:h2:latest.release")
    compile("ch.qos.logback:logback-classic:latest.release")
}