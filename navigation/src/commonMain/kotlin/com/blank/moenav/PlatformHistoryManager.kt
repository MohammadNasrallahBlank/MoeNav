package com.blank.moenav


@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class PlatformHistoryManager() {
    fun setOnUrlChangeListener(listener: (String) -> Unit)
    fun pushState(url: String)
    fun navigateBack()
    fun currentPath(): String
}
