package com.blank.moenav

import kotlinx.browser.window

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class PlatformHistoryManager actual constructor(){

    private var urlChangeListener: ((String) -> Unit)? = null

    actual fun setOnUrlChangeListener(listener: (String) -> Unit) {
        urlChangeListener = listener
        window.onpopstate = {
            urlChangeListener?.invoke(currentPath())
        }
    }

    actual fun pushState(url: String) {
        window.history.pushState(null, "", url)
    }

    actual fun navigateBack() {
        window.history.back()
    }

    actual fun currentPath(): String {
        return window.location.pathname
    }
}