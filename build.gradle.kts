import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import java.text.SimpleDateFormat
import java.util.*

plugins {
    id("net.minecraftforge.gradle")
    id("org.parchmentmc.librarian.forgegradle") version "1.+"
    `maven-publish`
    id("pl.allegro.tech.build.axion-release") version "1.13.14"
    kotlin("jvm") version "1.6.21"
    id("io.gitlab.arturbosch.detekt") version "1.21.0"
}

scmVersion {
    ignoreUncommittedChanges = false
    nextVersion {
        suffix = "next"
    }
    localOnly = true
    versionIncrementer("incrementPrerelease")
}

version = scmVersion.version
group = "eu.withoutaname.mod" // http://maven.apache.org/guides/mini/guide-naming-conventions.html
val archivesBaseName = "cityrpmod"

repositories {
    mavenCentral()
    maven("https://thedarkcolour.github.io/KotlinForForge/")
    maven("https://www.cursemaven.com")
}

sourceSets.main.get().resources { srcDir("src/generated/resources") }

dependencies {
    "minecraft"("net.minecraftforge:forge:1.16.5-36.2.35")

    implementation("thedarkcolour:kotlinforforge:1.16.0")

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.21.0")
}

minecraft {
//    mappings("official", "1.16.5")
    mappings("parchment", "2022.03.06-1.16.5")

    runs {
        create("client") {
            workingDirectory(project.file("run/client"))

            property("forge.logging.markers", "REGISTRIES")
            property("forge.logging.console.level", "debug")

            mods {
                create("cityrpmod") {
                    source(sourceSets.main.get())
                }
            }
        }

        create("server") {
            workingDirectory(project.file("run/server"))

            property("forge.logging.markers", "REGISTRIES")
            property("forge.logging.console.level", "debug")

            mods {
                create("cityrpmod") {
                    source(sourceSets.main.get())
                }
            }
        }

        create("data") {
            workingDirectory(project.file("run/data"))

            property("forge.logging.markers", "REGISTRIES")
            property("forge.logging.console.level", "debug")

            args(
                "--mod",
                "cityrpmod",
                "--all",
                "--output",
                file("src/generated/resources/"),
                "--existing",
                file("src/main/resources/")
            )

            mods {
                create("cityrpmod") {
                    source(sourceSets.main.get())
                }
            }
        }
    }
}

detekt {
    allRules = true
    autoCorrect = true
    buildUponDefaultConfig = true
    config = files("detekt.yml")
}

tasks.withType<Detekt>().configureEach {
    jvmTarget = "1.8"
}
tasks.withType<DetektCreateBaselineTask>().configureEach {
    jvmTarget = "1.8"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

tasks.processResources {
    inputs.property("version", version)
    filesMatching("META-INF/mods.toml") {
        expand(
            "version" to version
        )
    }
}

// Example for how to get properties into the manifest for reading by the runtime..
tasks.jar {
    manifest {
        attributes(
            mapOf(
                "Implementation-Title" to project.name,
                "Implementation-Version" to project.version,
                "Implementation-Timestamp" to SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(Date())
            )
        )
    }
}

tasks.jar { finalizedBy("reobfJar") }

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
    repositories {
        maven("file:///${project.projectDir}/mcmodsrepo")
    }
}
