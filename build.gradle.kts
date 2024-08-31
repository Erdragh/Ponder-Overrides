import java.util.Properties
import java.io.FileInputStream

plugins {
    java
    alias(libs.plugins.loom)
    alias(libs.plugins.machete)
}

base.archivesName = project.properties["archives_base_name"] as String
group = project.properties["maven_group"] as String

// Formats the mod version to include the Minecraft version and build number (if present)
// example: 1.0.0+1.18.2-100
val modVersion: String by project
val buildNumber = System.getenv("GITHUB_RUN_NUMBER")
version = "${modVersion}+${libs.versions.minecraft}" + (if (buildNumber != null) "-${buildNumber}" else "")


repositories {
    maven(url = "https://maven.shedaniel.me/") // Cloth Config, REI
    maven(url = "https://dvs1.progwml6.com/files/maven/") // JEI
    maven(url = "https://api.modrinth.com/maven") // LazyDFU
    maven(url = "https://maven.terraformersmc.com/releases/") // Mod Menu
    maven(url = "https://mvn.devos.one/snapshots/") // Create, Porting Lib, Forge Tags, Milk Lib, Registrate
    maven(url = "https://cursemaven.com") // Forge Config API Port
    maven(url = "https://maven.jamieswhiteshirt.com/libs-release") // Reach Entity Attributes
    maven(url = "https://jitpack.io/") // Mixin Extras, Fabric ASM
    maven(url = "https://maven.tterrag.com/") // Flywheel
    maven(url = "https://maven.architectury.dev") { // Shedaniel's maven (Architectury API)
        content {
            includeGroup("dev.architectury")
        }
    }
    maven(url = "https://maven.saps.dev/releases") { // saps.dev Maven (KubeJS and Rhino)
        content {
            includeGroup("dev.latvian.mods")
        }
    }
    maven(url = "https://raw.githubusercontent.com/Fuzss/modresources/main/maven/") {
        name = "Fuzs Mod Resources"
    }
}

dependencies {
	// Setup
	minecraft(libs.minecraft)
	mappings(loom.officialMojangMappings())
	modImplementation(libs.fabricLoader)

	// Create - dependencies are added transitively
	modImplementation(libs.create)

	// Development QOL
	modLocalRuntime(libs.lazydfu)
	modLocalRuntime(libs.modmenu)

    val recipeViewer: String by project
	// Recipe Viewers - Create Fabric supports JEI, REI, and EMI.
	// See root gradle.properties to choose which to use at runtime.
	when (recipeViewer.lowercase()) {
		"jei" -> modLocalRuntime(libs.jei)
		"rei" -> modLocalRuntime(libs.rei)
		"emi" -> modLocalRuntime(libs.emi)
		"disabled" -> {}
		else -> println("Unknown recipe viewer specified: ${recipeViewer}. Must be JEI, REI, EMI, or disabled.")
	}
	// if you would like to add integration with them, uncomment them here.
//    modCompileOnly("mezz.jei:jei-${minecraft_version}-fabric:${jei_fabric_version}")
//    modCompileOnly("mezz.jei:jei-${minecraft_version}-common:${jei_fabric_version}")
//    modCompileOnly("me.shedaniel:RoughlyEnoughItems-api-fabric:${rei_version}")
//    modCompileOnly("me.shedaniel:RoughlyEnoughItems-default-plugin-fabric:${rei_version}")
//    modCompileOnly("dev.emi:emi:${emi_version}")

	// KubeJS
	modImplementation(libs.kubejs)
}

tasks.processResources {
	// require dependencies to be the version compiled against or newer
    val replacements = mapOf(
        "archives_base_name" to base.archivesName.get(),
        "mod_version" to modVersion,
        "fabric_loader_version" to libs.versions.fabricLoader.get(),
        "create_version" to libs.versions.create.get(),
        "minecraft_version" to libs.versions.minecraft.get(),
        "kubejs_version" to libs.versions.kubejs.get()
    )
    inputs.properties(replacements)

	filesMatching("fabric.mod.json") {
		expand(replacements)
	}
}

machete {
	// disable machete locally for faster builds
	enabled = buildNumber != null
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(17)
	withSourcesJar()
}

tasks.jar {
	from("LICENSE") {
		rename { "${it}_${base.archivesName.get()}" }
	}
}

// Force fabric loader version to fix duplicate loader classes problem
configurations.configureEach {
    resolutionStrategy.force("net.fabricmc:fabric-loader:${libs.versions.fabricLoader.get()}")
}
