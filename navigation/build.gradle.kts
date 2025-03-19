import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
tasks.matching { it.name == "kotlinNpmInstall" }.configureEach { enabled = false }
val isRunningOnJitPack = System.getenv("JITPACK") == "true"


plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("convention.publication")
}

group = "com.blank.moenav"
version = "1.0.0"

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    if (!isRunningOnJitPack) {
        wasmJs {
            browser()
            binaries.library()
        }
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
