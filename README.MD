## 🛠️ MoeNav - A Navigation Library for Compose Web (WASM)
**MoeNav** is a **lightweight, flexible, and powerful** navigation library designed **exclusively for Jetpack Compose for Web (WASM)**. Inspired by **Android's Navigation Component**, it provides **NavHost**, **NavController**, and **nested navigation graphs**, solving issues related to **URLs, browser history, and deep linking**.

🚀 **Optimized for Kotlin Multiplatform (WASM) — Not for JVM or Android.**

---

## 📦 Installation
### Using Maven Central (Recommended)
1. **Ensure you have Maven Central in `settings.gradle.kts`**
```kotlin
dependencyResolutionManagement {
    repositories {
        mavenCentral() // ✅ Required for MoeNav
    }
}
```
2. **Add the dependency to your `build.gradle.kts`**
```kotlin
dependencies {
    implementation("com.blank.moenav:MoeNav:1.0.6")
}
```

---

## 🚀 Features
✅ **Jetpack Compose Web (WASM) Navigation** → Seamlessly navigate between screens.  
✅ **URL-Based Navigation** → Uses the browser's address bar for real URLs.  
✅ **Nested Navigation Graphs** → Organize routes into logical groups.  
✅ **Browser History Support** → `Back`/`Forward` buttons work as expected.  
✅ **Deep Linking** → Load specific pages directly via URL.  
✅ **Dynamic Parameters** → Pass values via URL (`/user/{id}`).  
✅ **OnDestinationChanged Listener** → Get notified when navigation changes.  

📌 **⚠️ This library is WASM-only. It does not work on JVM or Android.**

---

## 🔧 How to Use
### Step 1: Setup `NavHost`
Define a **NavHost** and specify your start destination:
```kotlin
@Composable
fun App(navController: MoeNavController<SampleRoutes>) {
    NavHost(navController = navController, startDestination = "/") {
        composable("/") { HomeScreen(navController) }
        composable("/profile") { ProfileScreen(navController) }
        composable("/about") { AboutScreen(navController) }
    }
}
```

---

### Step 2: Navigate Between Screens
Use `navController.navigate(route)` to switch between pages:
```kotlin
@Composable
fun HomeScreen(navController: MoeNavController<SampleRoutes>) {
    Column {
        Text("🏠 Home Screen")
        Button(onClick = { navController.navigate(SampleRoutes.Profile) }) {
            Text("Go to Profile")
        }
    }
}
```

---

### Step 3: Dynamic Parameters
Routes can include **path parameters**:
```kotlin
NavHost(navController = navController, startDestination = "/") {
    composable("/user/{userId}") { params ->
        val userId = params["userId"] ?: "Unknown"
        UserScreen(navController, userId)
    }
}
```
📌 **Navigating with Parameters**:
```kotlin
navController.navigate("/user/123")
```

---

### Step 4: Nested Navigation Graphs
Define **sub-graphs** for better organization:
```kotlin
NavHost(navController = navController, startDestination = "/") {
    composable("/loading") { LoadingScreen(navController) }

    navigation(route = "/auth", startDestination = "/auth/login") {
        composable("/auth/login") { LoginScreen(navController) }
        composable("/auth/register") { RegisterScreen(navController) }
    }
}
```
This lets you **group related screens** (`/auth/login`, `/auth/register`).

---

## 🌟 Advanced Features
### 🔄 Listening for Navigation Changes
Use `OnDestinationChangedListener` to track when the route changes:
```kotlin
navController.setOnDestinationChangedListener { route ->
    println("Navigated to: $route")
}
```

### 🔙 Handling Back Navigation
Enable browser **back button support**:
```kotlin
navController.enableBackNavigation()
```

---

## 📜 License
**MoeNav** is released under the [MIT License](LICENSE).

---

## 🤝 Contributing
Contributions are welcome! Feel free to:
- **Report bugs** 🐛
- **Request features** 🚀
- **Submit pull requests** 🎉

---

## 🔗 Links
- **GitHub**: [MoeNav Repo](https://github.com/MohammadNasrallahBlank/MoeNav)
- **Jetpack Compose for Web**: [Official Docs](https://github.com/JetBrains/compose-multiplatform)

---

## 🎉 Enjoy MoeNav!
A **modern, flexible, and developer-friendly navigation solution** for **Compose Web (WASM)**.

---

## 🙌 Credits

Built with ❤️ by [Mohamad](https://github.com/MohammadNasrallahBlank)
