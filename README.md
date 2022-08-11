# [Immersive Signals](https://www.curseforge.com/minecraft/mc-mods/immersive-signals "Immersive Signals on CurseForge") - Minecraft Forge Mod

![](https://img.shields.io/maven-metadata/v?label=1.16%20latest%20version&metadataUrl=https%3A%2F%2Frepo.withoutaname.eu%2Freleases%2Feu%2Fwithoutaname%2Fmod%2FImmersiveSignals%2Fmaven-metadata.xml&versionPrefix=1.16)

German Ks-signals for Immersive Railroading or other train mods.

## Maven

Groovy:

```groovy
repositories {
    maven { // Immersive Signals
        url "https://repo.withoutaname.eu/releases/"
    }
}

dependencies {
    implementation fg.deobf("eu.withoutaname.mod:ImmersiveSignals:${immersivesignals_version}")
}
```

Kotlin:

```kotlin
repositories {
    maven("https://repo.withoutaname.eu/releases/")
}

dependencies {
    implementation(fg.deopf("eu.withoutaname.mod:ImmersiveSignals:${immersivesignals_version}"))
}
```