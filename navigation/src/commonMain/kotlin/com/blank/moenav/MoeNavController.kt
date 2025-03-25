package com.blank.moenav

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.browser.window

class MoeNavController<T : WebRoute>(
    initialRoute: T,
    private val routeMatcher: RouteMatcher<T>
) {
    private val _currentRoute = mutableStateOf(initialRoute)
    val currentRoute: State<T> = _currentRoute

    private var historyManager: BrowserHistoryManager? = null

    private var routeChangeListener: ((T) -> Unit)? = null


    fun attachHistoryManager(manager: BrowserHistoryManager) {
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
        }
    }

    companion object {
        fun <T : WebRoute> createWithBrowserHistory(
            routes: List<(String) -> T?>,
            startDestination: T
        ): MoeNavController<T> {
            val routeMatcher = RouteMatcher(routes)
            val currentPath = window.location.pathname
            val initialRoute = if (currentPath == "/") {
                startDestination
            } else {
                routeMatcher.match(currentPath) ?: startDestination
            }

            val navController = MoeNavController(initialRoute, routeMatcher)
            val historyManager = BrowserHistoryManager()
            navController.attachHistoryManager(historyManager)

            // ✅ Push startDestination to browser if it was just `/`
            if (currentPath == "/") {
                historyManager.pushState(startDestination.buildUrl())
            }

            return navController
        }
    }

}


