plugins {
    id("java")
    id("io.freefair.lombok") version "8.13.1"
}

repositories {
    mavenCentral()
    maven("https://repo.foxikle.dev/cytonic")
}

dependencies {
    compileOnly("net.cytonic:Cytosis:1.0-SNAPSHOT")
    compileOnly(project(":core"))
}

tasks {
    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }
    }
    compileJava {
        options.release = 21
    }

    jar {
        archiveClassifier = "solos"
    }
}