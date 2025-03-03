package ca.weblite.ktswing.extensions

import ca.weblite.ktswing.AutoAddDisabled
import ca.weblite.ktswing.BorderPane
import java.awt.Component
import javax.swing.JSplitPane
import java.awt.Container
import java.util.WeakHashMap
import javax.swing.JComponent

private val containerFactoryMap =
    WeakHashMap<Container, MutableMap<Class<out Component>, () -> Component>>()

/**
 * Immutable (no setter) extension property that returns a MutableMap of factory functions.
 *
 * - If this Container has no map yet, a new one is created and stored in [containerFactoryMap].
 * - We return the same map on subsequent accesses, making it "lazy."
 * - The property itself is a `val` (read-only), but the map it returns *is* mutable.
 */
val Container.factory: MutableMap<Class<out Component>, () -> Component>
    get() = containerFactoryMap.getOrPut(this) { mutableMapOf() }

/**
 * Extension function to determine if automatic addition of components is enabled for a Container.
 *
 * @return `true` if components should be automatically added, `false` otherwise.
 */
fun Container.isAutoAddEnabled(): Boolean {
    return when (this) {
        is JSplitPane -> false
        is AutoAddDisabled -> false
        // Add other specialized containers here as needed
        else -> true
    }
}

fun <T: Component>Container.getFactoryForComponent(componentClass: Class<T>): (() -> T)? {
    val localFactory = factory[componentClass] ?: parent?.getFactoryForComponent(componentClass)

    return localFactory as (() -> T)?
}

fun <T:Component>Container.factory(componentClass: Class<T>, factory: () -> T) {
    this.factory[componentClass] = factory as () -> Component
}
