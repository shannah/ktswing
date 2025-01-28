package ca.weblite.ktswing.extensions

import ca.weblite.ktswing.AutoAddDisabled
import ca.weblite.ktswing.BorderPane
import javax.swing.JSplitPane
import java.awt.Container

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
