package com.blank.moenav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

@Composable
fun <T : WebRoute> NavHost(
    navController: MoeNavController<T>,
    startDestination: String,
    builder: NavGraphBuilder.() -> Unit
) {
    val graph = NavGraphBuilder(startDestination = startDestination).apply(builder)
    val currentRoute by navController.currentRoute

    RenderDestination(navController, currentRoute.path, graph.destinations)
}

@Composable
private fun <T : WebRoute> RenderDestination(
    navController: MoeNavController<T>,
    route: String,
    destinations: List<NavDestination>
) {
    for (destination in destinations) {
        when (destination) {
            is NavComposable -> {
                val params = RouteMatcher.matchPattern(route, destination.route)
                if (destination.route == route || params != null) {
                    destination.content(params)
                    return
                }
            }
            is NestedNavGraph -> {
                if (route.startsWith(destination.route)) {
                    RenderDestination(navController, route, destination.destinations)
                    return
                }
            }
        }
    }
}

