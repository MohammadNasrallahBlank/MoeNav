import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.blank.moenav.MoeNavController
import kotlinx.browser.document
import org.w3c.dom.HTMLElement
import sample.app.App
import sample.app.SampleRoutes

@OptIn(ExperimentalComposeUiApi::class)
fun main() {

    val navController = MoeNavController.createWithBrowserHistory(
        routes = SampleRoutes.routes,
        fallbackRoute = SampleRoutes.Home
    )

    val body: HTMLElement = document.body ?: return

    ComposeViewport(body) {
        App(navController)
    }
}
