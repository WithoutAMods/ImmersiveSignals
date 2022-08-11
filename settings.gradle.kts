rootProject.name = "ImmersiveSignals"
pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.minecraftforge.net")
        maven("https://maven.parchmentmc.org")
    }
    resolutionStrategy {
        eachPlugin {
            if(requested.id.id == "net.minecraftforge.gradle") {
                useModule("net.minecraftforge.gradle:ForgeGradle:5.1.+")
            }
        }
    }
}
