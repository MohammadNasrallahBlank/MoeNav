package com.blank.moenav

class RouteMatcher<T : WebRoute>(
    private val routes: List<(String) -> T?>
) {
    fun match(url: String): T? {
        routes.forEach { parser ->
            parser(url)?.let { return it }
        }
        return null
    }

    companion object {
        fun matchPattern(url: String, pattern: String): Map<String, String>? {
            val urlParts = url.trim('/').split("/")
            val patternParts = pattern.trim('/').split("/")

            if (urlParts.size != patternParts.size) return null

            val params = mutableMapOf<String, String>()
            patternParts.forEachIndexed { index, part ->
                when {
                    part.startsWith("{") && part.endsWith("}") -> {
                        val paramName = part.removeSurrounding("{", "}")
                        params[paramName] = urlParts[index]
                    }
                    part != urlParts[index] -> return null
                }
            }
            return params
        }
    }
}
