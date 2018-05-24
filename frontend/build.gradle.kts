import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import org.jetbrains.kotlin.gradle.frontend.KotlinFrontendExtension
import org.jetbrains.kotlin.gradle.frontend.npm.NpmExtension
import org.jetbrains.kotlin.gradle.frontend.webpack.WebPackExtension
import org.jetbrains.kotlin.gradle.dsl.Coroutines

val production: String by project
val kotlinVersion: String by project
val kotlinxHtmlVersion: String by project
val compileKotlin2Js: KotlinJsCompile by tasks
kotlin.experimental.coroutines = Coroutines.ENABLE

plugins {
    id("kotlin2js")
    id("org.jetbrains.kotlin.frontend")
}

repositories {
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
    jcenter()
    flatDir {
        dirs(
                "../../kotlin-vue-wrappers/kotlin-vue/build/libs",
                "../../kotlin-vue-wrappers/kotlin-vue-extensions/build/libs"
        )

    }
}

dependencies {
    compile(kotlin("stdlib-js", kotlinVersion))
    compile("org.jetbrains.kotlinx:kotlinx-html-js:$kotlinxHtmlVersion")
    compile("com.github.vkhikhlov:kotlin-vue:0.0.6")
    compile("com.github.vkhikhlov:kotlin-vue-extensions:0.0.2")
}

compileKotlin2Js.apply {
    kotlinOptions {
        metaInfo = true
        moduleKind = "commonjs"
        outputFile = "$projectDir/build/classes/main/${project.name}.js"
        if (!production.toBoolean()) {
            sourceMap = true
            sourceMapEmbedSources = "always"
            suppressWarnings = true
        }
    }
}

configure<KotlinFrontendExtension> {
    downloadNodeJsVersion = "latest"
    if (!production.toBoolean()) {
        sourceMaps = true
    }
    the(NpmExtension::class).apply {
        dependency("vue", "latest")
        dependency("jquery", "latest")
        dependency("semantic-ui", "latest")
        dependency("vue-router", "latest")

        devDependency("css-loader", "latest")
        devDependency("style-loader", "latest")
        devDependency("file-loader", "latest")
        devDependency("html-webpack-plugin", "latest")
        devDependency("webpack", "latest")
        devDependency("webpack-dev-server", "latest")
    }
    bundle("webpack", delegateClosureOf<WebPackExtension> {
        bundleName = "main"
        contentPath = file("src/main/resources")
        publicPath = if (production.toBoolean()) "/static/" else "/"
        port = 8088
        mode = if (production.toBoolean()) "production" else "development"
    })
    define("PRODUCTION", production.toBoolean())
}

val copyConfigs = task("copyConfigs") {
    doLast {
        copy {
            from("$projectDir/configs")
            into("$buildDir")
        }
    }
}

tasks.whenTaskAdded {
    if (name == "npm-configure") {
        dependsOn(copyConfigs)
    }
}