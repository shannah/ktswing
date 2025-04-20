package ca.weblite.swinky

import ca.weblite.swinky.extensions.isAutoAddEnabled
import java.awt.Container

/**
 * Extension function to add a [BorderPane] to any [Container].
 *
 * Usage Example:
 * ```
 * borderPane {
 *     north = label { text = "North" }
 *     center = panel {
 *         button { text = "Click Me" }
 *     }
 * }
 * ```
 */
fun Container.borderPane(init: BorderPane.() -> Unit = {}): BorderPane {
    val borderPane = BorderPane()
    borderPane.init()
    if (isAutoAddEnabled()) {
        this.add(borderPane)
    }
    return borderPane
}
