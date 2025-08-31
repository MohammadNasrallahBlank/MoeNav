package com.blank.moenav

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class PlatformHistoryManager actual constructor() {

    private var urlChangeListener: ((String) -> Unit)? = null
    private val history = mutableListOf<String>()
    private var currentIndex = -1

    actual fun setOnUrlChangeListener(listener: (String) -> Unit) {
        urlChangeListener = listener
    }

    actual fun pushState(url: String) {
        if (currentIndex < history.lastIndex) {
            history.subList(currentIndex + 1, history.size).clear()
        }

        history.add(url)
        currentIndex++
        urlChangeListener?.invoke(url)
    }

    actual fun navigateBack() {
        if (currentIndex > 0) {
            currentIndex--
            urlChangeListener?.invoke(history[currentIndex])
        }
    }

    actual fun currentPath(): String {
        return if (currentIndex >= 0) history[currentIndex] else "/"
    }
}
