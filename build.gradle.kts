import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import java.text.SimpleDateFormat
import java.util.Date

plugins {
    id("net.minecraftforge.gradle")
    id("org.parchmentmc.librarian.forgegradle") version "1.+"
    `maven-publish`
    id("pl.allegro.tech.build.axion-release") version "1.13.14"
    kotlin("jvm") version "1.7.10"
    kotlin("plugin.serialization") version "1.7.10"
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
val archivesBaseName = "immersivesignals"

repositories {
    mavenCentral()
    maven("https://www.cursemaven.com")
    maven("https://teamopenindustry.cc/maven/")
    maven("https://squiddev.cc/maven/")
}

sourceSets {
    main.get().resources { srcDir("src/generated/resources") }
    create("dependencies") {
        allJava.setSrcDirs(listOf<Any>())
        resources.setSrcDirs(listOf<Any>())
    }
}

configurations {
    val mod by creating
    val modRuntime by creating
    val compileOnly by getting
    val dependenciesRuntimeOnly by getting
    modRuntime.extendsFrom(mod)
    compileOnly.extendsFrom(mod)
    dependenciesRuntimeOnly.extendsFrom(modRuntime)
}

dependencies {
    "minecraft"("net.minecraftforge:forge:1.16.5-36.2.35")

    "modRuntime"(fg.deobf("cam72cam.universalmodcore:UniversalModCore:1.16.5-forge-1.1.1"))
    "modRuntime"(fg.deobf("org.squiddev:cc-tweaked-1.16.5:1.97.0"))
    "modRuntime"(fg.deobf("trackapi:TrackAPI:1.16.5-forge-1.2"))
    "mod"(fg.deobf("cam72cam.immersiverailroading:ImmersiveRailroading:1.16.5-forge-1.9.1"))

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.0")

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
                create("immersivesignals") {
                    sources(sourceSets.main.get(), sourceSets["dependencies"])
                }
            }
        }

        create("server") {
            workingDirectory(project.file("run/server"))

            property("forge.logging.markers", "REGISTRIES")
            property("forge.logging.console.level", "debug")

            mods {
                create("immersivesignals") {
                    sources(sourceSets.main.get(), sourceSets["dependencies"])
                }
            }
        }

        create("data") {
            workingDirectory(project.file("run/data"))

            property("forge.logging.markers", "REGISTRIES")
            property("forge.logging.console.level", "debug")

            args(
                "--mod",
                "immersivesignals",
                "--all",
                "--output",
                file("src/generated/resources/"),
                "--existing",
                file("src/main/resources/")
            )

            mods {
                create("immersivesignals") {
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
    val props = mapOf(
        "version" to version
    )
    props.forEach { (name, value) ->
        inputs.property(name, value)
    }
    filesMatching("META-INF/mods.toml") {
        expand(props)
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
