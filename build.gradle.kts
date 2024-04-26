plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.10"
    id("io.papermc.paperweight.userdev") version "1.5.10"
    id ("xyz.jpenilla.run-paper") version "2.2.2" // Adds runServer task for testing
}

group = "zinc.doiche"
version = "1.0"
description = "ui helper"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    paperweight.paperDevBundle("1.20.1-R0.1-SNAPSHOT")
    compileOnly("com.google.code.gson:gson:2.10.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation("io.github.monun:kommand-api:3.1.7")
    implementation("org.mongodb:mongodb-driver-sync:4.9.0")
    implementation(kotlin("reflect"))
}

//의존성 탐색하도록 설정(duplicatesStrategy 설정시 필요)
configurations.implementation.configure {
    isCanBeResolved = true
}

tasks {
    jar {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        from(configurations.implementation.get()
            .filter{
//                println(it.name)
                !it.name.contains("kommand") && !it.name.contains("kotlin-")
            }
            .map { if (it.isDirectory) it else zipTree(it) })
    }
    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(17))
    }
    compileJava {
        options.encoding = "UTF-8" // We want UTF-8 for everything
        options.release.set(17)
    }
    javadoc {
        options.encoding = "UTF-8" // We want UTF-8 for everything
    }
    assemble {
        dependsOn(reobfJar)
    }
}