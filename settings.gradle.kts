enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
	repositories {
		maven(url = "https://maven.fabricmc.net/")
		maven(url = "https://server.bbkr.space/artifactory/libs-release/")
		mavenCentral()
		gradlePluginPortal()
	}
}

dependencyResolutionManagement {
    versionCatalogs.create("libs")
}

rootProject.name = "PonderOverrides"
