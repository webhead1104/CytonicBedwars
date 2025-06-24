plugins {
    id("java")
    id("com.gradleup.shadow") version "9.0.0-beta15"
    id("io.freefair.lombok") version "8.13.1" apply false
}

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://repo.foxikle.dev/cytonic")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":solos"))
    implementation(project(":doubles"))
}

var bedwarsVersion = "1.1"

allprojects {
    group = "net.cytonic"
    version = bedwarsVersion
    description = "The CytonicMC bedwars plugin"
}

java.sourceCompatibility = JavaVersion.VERSION_21

tasks {
    assemble {
        dependsOn(shadowJar)
    }
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release = 21
    }
    shadowJar {
        archiveClassifier.set("")
        mergeServiceFiles()
        doLast {
            if (providers.gradleProperty("server_dir").isPresent) {
                val serverDir = file(providers.gradleProperty("server_dir").get())
                copy {
                    from(archiveFile)
                    into(serverDir.resolve("plugins"))
                }
            }
        }
    }
}