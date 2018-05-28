import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

external fun compile(name: String)
configure<KotlinProjectExtension> { experimental.coroutines = Coroutines.ENABLE }

val kotlinVersion: String by project
val ktorVersion: String by project
val exposedVersion: String by project
val compileKotlin: KotlinCompile by tasks
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

fun DependencyHandlerScope.ktor(module: String) = compile("io.ktor:ktor-$module:$ktorVersion")

dependencies {
    compile(kotlin("stdlib-jdk8", kotlinVersion) as String)
    ktor("server-netty")
    ktor("gson")
    ktor("locations")
    compile("org.jetbrains.exposed:exposed:$exposedVersion")
    compile("com.h2database:h2:latest.release")
    compile("ch.qos.logback:logback-classic:latest.release")
}