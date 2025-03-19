package com.blank.moenav

import androidx.compose.runtime.Composable

sealed interface NavDestination

class NavComposable(
    val route: String,
    val content: @Composable (Map<String, String>?) -> Unit
) : NavDestination


class NestedNavGraph(
    val route: String,
    val startDestination: String,
    val destinations: List<NavDestination>
) : NavDestination

@DslMarker
annotation class NavGraphDsl

@NavGraphDsl
class NavGraphBuilder(val route: String? = null, val startDestination: String? = null) {

    private val _destinations = mutableListOf<NavDestination>()
    val destinations: List<NavDestination> get() = _destinations

    fun composable(route: String, content: @Composable (Map<String, String>?) -> Unit) {
        _destinations += NavComposable(route, content)
    }

    fun navigation(
        route: String,
        startDestination: String,
        nestedGraphBuilder: NavGraphBuilder.() -> Unit
    ) {
        val nestedBuilder = NavGraphBuilder(route, startDestination).apply(nestedGraphBuilder)
        _destinations += NestedNavGraph(route, startDestination, nestedBuilder.destinations)
    }
}
