package ca.weblite.ktswing.style

interface Selector {
    fun matches(target: Any): Boolean
}