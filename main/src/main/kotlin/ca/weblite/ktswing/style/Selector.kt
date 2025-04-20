package ca.weblite.swinky.style

interface Selector {
    fun matches(target: Any): Boolean
}