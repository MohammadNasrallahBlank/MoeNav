package com.blank.moenav

interface WebRoute {
    val path: String
    val parameters: Map<String, String>

    fun buildUrl(): String {
        var url = path
        parameters.forEach { (key, value) ->
            url = url.replace("{$key}", value)
        }
        return url
    }
}