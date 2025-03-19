package com.blank.moenav

import kotlinx.browser.window

class BrowserHistoryManager {

    private var urlChangeListener: ((String) -> Unit)? = null

    fun setOnUrlChangeListener(listener: (String) -> Unit) {
        urlChangeListener = listener
        window.onpopstate = {
            urlChangeListener?.invoke(currentPath())
        }
    }

    fun pushState(url: String) {
        window.history.pushState(null, "", url)
    }

    fun navigateBack() {
        window.history.back()
    }

    private fun currentPath(): String {
        return window.location.pathname
    }
}
