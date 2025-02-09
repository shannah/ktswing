package ca.weblite.ktswing.extensions

import java.awt.Component
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.util.*
import javax.swing.JComponent


// A registry mapping each component to a map that groups listeners by event key,
// and then maps an identifier to its MouseAdapter.
private val mouseListenerRegistry: MutableMap<Component, MutableMap<String, MutableMap<Any, MouseAdapter>>> =
    WeakHashMap()

val Component.classList: MutableSet<String>
    get() = if (this is JComponent) object : AbstractMutableSet<String>() {

        // Recompute the delegate list from the "cssClasses" client property.
        // (This list is always a space‐separated string.)
        private val delegate: MutableSet<String>
            get() = getClientProperty("cssClasses")
                ?.toString()
                ?.split(" ")
                ?.filter { it.isNotEmpty() }
                ?.toMutableSet() ?: mutableSetOf()

        override val size: Int get() = delegate.size
        override fun iterator(): MutableIterator<String> = delegate.iterator()

        override fun add(element: String): Boolean {
            val tmp = delegate
            if (tmp.add(element)) {
                flush(tmp)
                return true
            }
            return false
        }

        override fun remove(element: String): Boolean {
            val tmp = delegate
            if (tmp.remove(element)) {
                flush(tmp)
                return true
            }
            return false
        }

        // Update the "cssClasses" client property.
        private fun flush(del: Set<String> = delegate) {
            putClientProperty("cssClasses", del.joinToString(" "))
        }
    } else mutableSetOf()

fun Component.onMouseEntered(action: (MouseEvent) -> Unit) {
    addMouseListener(object : MouseAdapter() {
        override fun mouseEntered(e: MouseEvent) = action(e)
    })
}

fun Component.onMouseExited(action: (MouseEvent) -> Unit) {
    addMouseListener(object : MouseAdapter() {
        override fun mouseExited(e: MouseEvent) = action(e)
    })
}

fun Component.onMousePressed(action: (MouseEvent) -> Unit) {
    addMouseListener(object : MouseAdapter() {
        override fun mousePressed(e: MouseEvent) = action(e)
    })
}

fun Component.onMouseReleased(action: (MouseEvent) -> Unit) {
    addMouseListener(object : MouseAdapter() {
        override fun mouseReleased(e: MouseEvent) = action(e)
    })
}

fun Component.onMouseClicked(action: (MouseEvent) -> Unit) {
    addMouseListener(object : MouseAdapter() {
        override fun mouseClicked(e: MouseEvent) = action(e)
    })
}


/**
 * Retrieves (or creates) the map for the given event type on this component.
 */
private fun Component.getMouseListenerMapFor(eventKey: String): MutableMap<Any, MouseAdapter> {
    return mouseListenerRegistry.getOrPut(this) { mutableMapOf() }
        .getOrPut(eventKey) { mutableMapOf() }
}

/**
 * Adds a mouse listener for the "mouse entered" event.
 * If a listener with the same [id] was previously added on this component,
 * it is removed before the new one is added.
 */
fun Component.onMouseEntered(id: Any, action: (MouseEvent) -> Unit) {
    val eventKey = "mouseEntered"
    val listeners = getMouseListenerMapFor(eventKey)
    // Remove a previously added listener for this event & id, if any.
    listeners[id]?.let { removeMouseListener(it) }
    // Create a new adapter that calls the provided action.
    val adapter = object : MouseAdapter() {
        override fun mouseEntered(e: MouseEvent) = action(e)
    }
    listeners[id] = adapter
    addMouseListener(adapter)
}

/**
 * Adds a mouse listener for the "mouse exited" event.
 */
fun Component.onMouseExited(id: Any, action: (MouseEvent) -> Unit) {
    val eventKey = "mouseExited"
    val listeners = getMouseListenerMapFor(eventKey)
    listeners[id]?.let { removeMouseListener(it) }
    val adapter = object : MouseAdapter() {
        override fun mouseExited(e: MouseEvent) = action(e)
    }
    listeners[id] = adapter
    addMouseListener(adapter)
}

/**
 * Adds a mouse listener for the "mouse pressed" event.
 */
fun Component.onMousePressed(id: Any, action: (MouseEvent) -> Unit) {
    val eventKey = "mousePressed"
    val listeners = getMouseListenerMapFor(eventKey)
    listeners[id]?.let { removeMouseListener(it) }
    val adapter = object : MouseAdapter() {
        override fun mousePressed(e: MouseEvent) = action(e)
    }
    listeners[id] = adapter
    addMouseListener(adapter)
}

/**
 * Adds a mouse listener for the "mouse released" event.
 */
fun Component.onMouseReleased(id: Any, action: (MouseEvent) -> Unit) {
    val eventKey = "mouseReleased"
    val listeners = getMouseListenerMapFor(eventKey)
    listeners[id]?.let { removeMouseListener(it) }
    val adapter = object : MouseAdapter() {
        override fun mouseReleased(e: MouseEvent) = action(e)
    }
    listeners[id] = adapter
    addMouseListener(adapter)
}

/**
 * Adds a mouse listener for the "mouse clicked" event.
 */
fun Component.onMouseClicked(id: Any, action: (MouseEvent) -> Unit) {
    val eventKey = "mouseClicked"
    val listeners = getMouseListenerMapFor(eventKey)
    listeners[id]?.let { removeMouseListener(it) }
    val adapter = object : MouseAdapter() {
        override fun mouseClicked(e: MouseEvent) = action(e)
    }
    listeners[id] = adapter
    addMouseListener(adapter)
}