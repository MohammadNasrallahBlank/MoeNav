package sample.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.blank.moenav.MoeNavController
import com.blank.moenav.NavHost


@Composable
fun App(navController: MoeNavController<SampleRoutes>) {
    NavHost(navController = navController) {
        composable("/") { HomeScreen(navController) }
        composable("/about") { AboutScreen(navController) }
        composable("/user/{userId}") { params ->
            val userId = params?.get("userId").orEmpty()
            UserScreen(navController, userId)
        }
        navigation(route = "/profile", startDestination = "/profile/main") {
            composable("/profile/main") { ProfileMainScreen(navController) }
            composable("/profile/details") { ProfileDetailsScreen(navController) }
        }
    }
}


@Composable
fun UserScreen(navController: MoeNavController<SampleRoutes>, userId: String) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("👤 User ID: $userId")
        Button(onClick = { navController.navigateBack() }) {
            Text("Go Back")
        }
    }
}


@Composable
fun HomeScreen(navController: MoeNavController<SampleRoutes>) {
    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Column {
            BasicText("🏠 Home Screen")
            Button(onClick = { navController.navigate(SampleRoutes.ProfileMain) }) {
                Text("Go to Profile")
            }
            Button(onClick = { navController.navigate(SampleRoutes.About) }) {
                Text("Go to About")
            }
        }
    }
}

@Composable
fun ProfileMainScreen(navController: MoeNavController<SampleRoutes>) {
    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Column {
            BasicText("👤 Profile Main Screen")
            Button(onClick = { navController.navigate(SampleRoutes.ProfileDetails) }) {
                Text("Go to Profile Details")
            }
            Button(onClick = { navController.navigate(SampleRoutes.Home) }) {
                Text("Go to Home")
            }
        }
    }
}

@Composable
fun ProfileDetailsScreen(navController: MoeNavController<SampleRoutes>) {
    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Column {
            BasicText("📑 Profile Details Screen")
            Button(onClick = { navController.navigate(SampleRoutes.ProfileMain) }) {
                Text("Back to Profile Main")
            }
            Button(onClick = { navController.navigate(SampleRoutes.About) }) {
                Text("Go to About")
            }
        }
    }
}


@Composable
fun AboutScreen(navController: MoeNavController<SampleRoutes>) {
    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Column {
            BasicText("ℹ️ About Screen")
            Button(onClick = { navController.navigate(SampleRoutes.Home) }) {
                Text("Go to Home")
            }
            Button(onClick = { navController.navigate(SampleRoutes.ProfileMain) }) {
                Text("Go to Profile")
            }
        }
    }
}