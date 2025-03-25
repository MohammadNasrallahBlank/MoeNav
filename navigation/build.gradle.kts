import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("convention.publication")
    id("com.vanniktech.maven.publish") version "0.31.0-rc2"
    signing
}

signing {
    useGpgCmd()
    sign(publishing.publications)
}

group = "com.blank.moenav"
version = "1.1.1"

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.library()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

    }
}

mavenPublishing {
    tasks.withType<Jar>().configureEach {
        if (name == "javadocJar") {
            archiveClassifier.set("javadoc")
        }
    }

    coordinates(
        groupId = "io.blankonline.moe",
        artifactId = "MoeNav",
        version = "1.1.1"
    )

    pom {
        name.set("MoeNav")
        description.set("MoeNav is a lightweight navigation library for Jetpack Compose for Web (WASM). It provides URL-based navigation, deep linking, nested navigation graphs, and browser history support, making Compose Web development more seamless.")
        inceptionYear.set("2025")
        url.set("https://github.com/MohammadNasrallahBlank/MoeNav")

        licenses {
            license {
                name.set("MIT")
                url.set("https://github.com/MohammadNasrallahBlank/MoeNav/blob/main/LICENSE")
            }
        }

        developers {
            developer {
                id.set("MohammadNasrallahBlank")
                name.set("Mohamad Nasrallah")
                email.set("Moe@blankonline.io")
            }
        }

        scm {
            url.set("https://github.com/MohammadNasrallahBlank/MoeNav")
        }
    }

    afterEvaluate {
        tasks.withType<Jar>().configureEach {
            if (archiveClassifier.getOrElse("") == "javadoc") {
                enabled = false
            }
        }
    }

    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()
}