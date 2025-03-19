package sample.app

import com.blank.moenav.RouteMatcher
import com.blank.moenav.WebRoute

sealed class SampleRoutes(
    override val path: String,
    override val parameters: Map<String, String> = emptyMap()
) : WebRoute {
    object Home : SampleRoutes("/")
    object About : SampleRoutes("/about")
    object ProfileMain : SampleRoutes("/profile/main")
    object ProfileDetails : SampleRoutes("/profile/details")
    data class User(val userId: String) : SampleRoutes("/user/$userId", mapOf("userId" to userId))

    companion object {
        val routes = listOf<(String) -> SampleRoutes?>(
            { url -> if (url == "/") Home else null },
            { url -> if (url == "/about") About else null },
            { url -> if (url == "/profile/main") ProfileMain else null },
            { url -> if (url == "/profile/details") ProfileDetails else null },
            { url ->
                RouteMatcher.matchPattern(url, "/user/{userId}")?.let { params ->
                    User(params["userId"]!!)
                }
            },
        )
    }
}


