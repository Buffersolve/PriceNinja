plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    kotlin("plugin.serialization") version "1.9.22"
    application
}

group = "ua.priceninja"
version = "1.0.0"
application {
    mainClass.set("ua.priceninja.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
}

dependencies {
    implementation(projects.shared)
    implementation(libs.logback)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)

    implementation("io.ktor:ktor-serialization:1.6.4")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")

//    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.10")
    implementation("org.jsoup:jsoup:1.17.2")

//    implementation("io.insert-koin:koin-bom:3.5.6")
    implementation("io.insert-koin:koin-core:3.5.6")

    implementation("io.ktor:ktor-client-core-jvm:2.3.10")
    implementation("io.ktor:ktor-client-okhttp:2.3.10")

//    implementation("com.h2database:h2:2.2.220")
//    implementation("org.jetbrains.exposed:exposed:0.35.2")

//    implementation("com.squareup.sqldelight:android-driver:1.5.1")
//    implementation("com.squareup.sqldelight:runtime:1.5.1")

    val exposed_version = "0.50.0"
    val h2_version = "2.2.220"

    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("com.h2database:h2:$h2_version")

    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.kotlin.test.junit)
}