import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

group = "com.github.MohammadNasrallahBlank"
version = "1.0.0"

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "sampleApp"
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "sampleApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        add(rootDirPath)
                        add(projectDirPath)
                    }
                }
            }
        }
        binaries.executable()
    }
    jvm()

    sourceSets {
        // 👇 This is what was missing
        val commonMain by getting {
            dependencies {
                implementation(project(":navigation"))
                implementation(compose.runtime)
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material3)
            }
        }

        val wasmJsMain by getting {
            // usually empty, unless you want wasm-only deps
        }

        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation("androidx.collection:collection-jvm:1.5.0")
                implementation("androidx.lifecycle:lifecycle-runtime:2.8.4")
                implementation("androidx.lifecycle:lifecycle-viewmodel:2.8.4")
                implementation("org.jetbrains.skiko:skiko-awt-runtime-windows-x64:0.8.19")
            }
        }
    }
}
compose.desktop {
    application {
        mainClass = "sample.app.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "MyApp"
            packageVersion = "1.0.0"
        }
    }
}

tasks.register<JavaExec>("runDesktop") {
    group = "application"
    description = "Run Compose Desktop sample"

    val jvmMainCompilation = kotlin.jvm().compilations.getByName("main")

    classpath = jvmMainCompilation.runtimeDependencyFiles + jvmMainCompilation.output.classesDirs
    mainClass.set("sample.app.MainKt")
}






