package com.blank.moenav

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

class MoeNavController<T : WebRoute>(
    initialRoute: T,
    private val routeMatcher: RouteMatcher<T>
) {
    private val _currentRoute = mutableStateOf(initialRoute)
    val currentRoute: State<T> = _currentRoute

    private var historyManager: PlatformHistoryManager? = null

    private var routeChangeListener: ((T) -> Unit)? = null

    fun attachHistoryManager(manager: PlatformHistoryManager) {
        historyManager = manager
        historyManager?.setOnUrlChangeListener { url ->
            handleUrlChange(url)
        }
    }

    fun navigate(route: T) {
        _currentRoute.value = route
        historyManager?.pushState(route.buildUrl())
    }

    fun setOnRouteChangeListener(listener: (T) -> Unit) {
        this.routeChangeListener = listener
    }

    fun removeOnRouteChangeListener() {
        this.routeChangeListener = null
    }

    fun navigateBack() {
        historyManager?.navigateBack()
    }

    private fun handleUrlChange(url: String) {
        routeMatcher.match(url)?.let {
            _currentRoute.value = it
            routeChangeListener?.invoke(it)
        }
    }

    companion object {
        fun <T : WebRoute> createWithHistory(
            routes: List<(String) -> T?>,
            startDestination: T
        ): MoeNavController<T> {
            val routeMatcher = RouteMatcher(routes)
            val historyManager = PlatformHistoryManager()

            val currentPath = historyManager.currentPath()
            val initialRoute = if (currentPath == "/" || currentPath.isBlank()) {
                startDestination
            } else {
                routeMatcher.match(currentPath) ?: startDestination
            }

            val navController = MoeNavController(initialRoute, routeMatcher)
            navController.attachHistoryManager(historyManager)

            if (currentPath == "/" || currentPath.isBlank()) {
                historyManager.pushState(startDestination.buildUrl())
            }

            return navController
        }
    }
}
