package sample.app

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.blank.moenav.MoeNavController

fun main() = application {
    val navController = MoeNavController.createWithHistory(
        routes = SampleRoutes.routes,
        startDestination = SampleRoutes.Home
    )

    Window(
        onCloseRequest = ::exitApplication,
        title = "MoeNav Sample"
    ) {
        App(navController)
    }
}
